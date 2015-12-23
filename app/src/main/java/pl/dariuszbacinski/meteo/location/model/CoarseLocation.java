package pl.dariuszbacinski.meteo.location.model;

import android.util.Log;

import com.google.android.gms.location.LocationRequest;
import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

@DebugLog
public class CoarseLocation {
    private LocationNameResolver locationNameResolver;
    private ReactiveLocationProvider reactiveLocationProvider;
    private RxPermissions rxPermissions;
    private MeteoService meteoService;

    @Inject
    public CoarseLocation(ReactiveLocationProvider reactiveLocationProvider, RxPermissions rxPermissions, MeteoService meteoService, LocationNameResolver locationNameResolver) {
        this.reactiveLocationProvider = reactiveLocationProvider;
        this.rxPermissions = rxPermissions;
        this.meteoService = meteoService;
        this.locationNameResolver = locationNameResolver;
    }

    public Observable<Location> requestLocation() {
        return rxPermissions.request(ACCESS_COARSE_LOCATION)
                .flatMap(new RequestCoarseLocationFunc(reactiveLocationProvider))
                .flatMap(new CoarseLocationToLocationFunc(meteoService, locationNameResolver));
//                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    static class RequestCoarseLocationFunc implements Func1<Boolean, Observable<android.location.Location>> {

        private ReactiveLocationProvider reactiveLocationProvider;

        public RequestCoarseLocationFunc(ReactiveLocationProvider reactiveLocationProvider) {
            this.reactiveLocationProvider = reactiveLocationProvider;
        }

        @Override
        public Observable<android.location.Location> call(Boolean coarseLocationPermissionGranted) {
            Log.i("CoarseLocation", "coarseLocationPermissionGranted: " + coarseLocationPermissionGranted);
            if (!coarseLocationPermissionGranted) {
                return Observable.error(new SecurityException("Coarse location is not granted"));
            } else {
                return getCoarseLocation();
            }
        }

        private Observable<android.location.Location> getCoarseLocation() {
            return reactiveLocationProvider.getUpdatedLocation(new LocationRequest().setPriority(LocationRequest.PRIORITY_LOW_POWER));
        }
    }

    static class CoarseLocationToLocationFunc implements Func1<android.location.Location, Observable<Location>> {

        private MeteoService meteoService;
        private LocationNameResolver locationNameResolver;

        public CoarseLocationToLocationFunc(MeteoService meteoService, LocationNameResolver locationNameResolver) {
            this.meteoService = meteoService;
            this.locationNameResolver = locationNameResolver;
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
            return locationNameResolver.getLocationName(location);
        }

        private Observable<Location> getLocationGridCoordinates(android.location.Location location) {
            return meteoService.getGridCoordinatedBasedOnLocation(location);
        }
    }
}
