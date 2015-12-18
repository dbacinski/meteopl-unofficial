package pl.dariuszbacinski.meteo.location.model;

import android.util.Log;

import com.google.android.gms.location.LocationRequest;
import com.tbruyelle.rxpermissions.RxPermissions;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

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
        return rxPermissions.request(ACCESS_COARSE_LOCATION)
                .flatMap(new RequestCoarseLocationFunc(reactiveLocationProvider))
                .flatMap(new CoarseLocationToLocationFunc(reactiveLocationProvider, meteoService))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    private static class RequestCoarseLocationFunc implements Func1<Boolean, Observable<android.location.Location>> {

        private ReactiveLocationProvider reactiveLocationProvider;

        public RequestCoarseLocationFunc(ReactiveLocationProvider reactiveLocationProvider) {
            this.reactiveLocationProvider = reactiveLocationProvider;
        }

        @Override
        public Observable<android.location.Location> call(Boolean coarseLocationPermissionGranted) {
            Log.i("CoarseLocation", "coarseLocationPermissionGranted: " + coarseLocationPermissionGranted);
            if (!coarseLocationPermissionGranted) {
                throw Exceptions.propagate(new SecurityException("Coarse location is not granted"));
            } else {
                return getCoarseLocation();
            }
        }

        private Observable<android.location.Location> getCoarseLocation() {
            return reactiveLocationProvider.getUpdatedLocation(new LocationRequest().setPriority(LocationRequest.PRIORITY_LOW_POWER));
        }
    }

    private static class CoarseLocationToLocationFunc implements Func1<android.location.Location, Observable<Location>> {

        private ReactiveLocationProvider reactiveLocationProvider;
        private MeteoService meteoService;

        public CoarseLocationToLocationFunc(ReactiveLocationProvider reactiveLocationProvider, MeteoService meteoService) {
            this.reactiveLocationProvider = reactiveLocationProvider;
            this.meteoService = meteoService;
        }

        @Override
        public Observable<Location> call(android.location.Location location) {
            Observable<Location> locationGridCoordinates = getLocationGridCoordinates(location);
            Observable<String> locationName = getLocationName(location);
            return locationGridCoordinates.zipWith(locationName, mergeLocationWithName());
        }

        private Func2<Location, String, Location> mergeLocationWithName() {
            return new Func2<Location, String, Location>() {
                @Override
                public Location call(Location location, String name) {
                    return location.setName(name);
                }
            };
        }

        private Observable<String> getLocationName(android.location.Location location) {
            return new LocationNameResolver(reactiveLocationProvider).getLocationName(location);
        }

        private Observable<Location> getLocationGridCoordinates(android.location.Location location) {
            return meteoService.getGridCoordinatedBasedOnLocation(location).subscribeOn(Schedulers.io());
        }
    }
}
