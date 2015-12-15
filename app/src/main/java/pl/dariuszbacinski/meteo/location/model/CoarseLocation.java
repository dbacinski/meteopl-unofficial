package pl.dariuszbacinski.meteo.location.model;

import android.Manifest;
import android.location.Address;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.location.LocationRequest;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import static com.eccyan.optional.Optional.ofNullable;

public class CoarseLocation {
    private ReactiveLocationProvider reactiveLocationProvider;
    private RxPermissions rxPermissions;
    private MeteoService meteoService;

    public CoarseLocation(ReactiveLocationProvider reactiveLocationProvider, RxPermissions rxPermissions, MeteoService meteoService) {
        this.reactiveLocationProvider = reactiveLocationProvider;
        this.rxPermissions = rxPermissions;
        this.meteoService = meteoService;
    }

    public Observable<Location> requestLocation() {
        return rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .flatMap(new Func1<Boolean, Observable<android.location.Location>>() {
                    @Override
                    public Observable<android.location.Location> call(Boolean coarseLocationPermissionGranted) {
                        Log.i("CoarseLocation", "coarseLocationPermissionGranted: " + coarseLocationPermissionGranted);
                        if (!coarseLocationPermissionGranted) {
                            throw Exceptions.propagate(new SecurityException("Coarse location is not granted"));
                        } else {
                            return getUpdatedCoarseLocation();
                        }
                    }
                }).flatMap(new Func1<android.location.Location, Observable<Location>>() {
                    @Override
                    public Observable<Location> call(android.location.Location location) {
                        return getLocationName(location).zipWith(getLocationGridCoordinates(location), new Func2<String, Location, Location>() {
                            @Override
                            public Location call(String s, Location location) {
                                return location.setName(s);
                            }
                        });
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<android.location.Location> getUpdatedCoarseLocation() {
        return reactiveLocationProvider.getUpdatedLocation(new LocationRequest().setPriority(LocationRequest.PRIORITY_LOW_POWER));
    }

    @NonNull
    private Observable<String> getLocationName(android.location.Location location) {
        return reactiveLocationProvider
                .getReverseGeocodeObservable(location.getLatitude(), location.getLongitude(), 1)
                .map(new Func1<List<Address>, String>() {
                    @Override
                    public String call(List<Address> addresses) {
                        for(Address address : addresses){
                            return ofNullable(address.getLocality()).orElse(ofNullable(address.getFeatureName()).orElse("N/A"));
                        }
                        return "N/A";
                    }
                });
    }

    private Observable<Location> getLocationGridCoordinates(android.location.Location location) {
        return meteoService.getGridCoordinatedBasedOnLocation(location).subscribeOn(Schedulers.io());
    }
}
