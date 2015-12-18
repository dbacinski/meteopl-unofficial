package pl.dariuszbacinski.meteo.location.model;

import android.location.Address;

import java.util.List;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.functions.Func1;

import static com.eccyan.optional.Optional.ofNullable;

class LocationNameResolver {

    private ReactiveLocationProvider reactiveLocationProvider;

    public LocationNameResolver(ReactiveLocationProvider reactiveLocationProvider) {
        this.reactiveLocationProvider = reactiveLocationProvider;
    }

    public Observable<String> getLocationName(android.location.Location location) {
        return reactiveLocationProvider
                .getReverseGeocodeObservable(location.getLatitude(), location.getLongitude(), 1)
                .map(new Func1<List<Address>, String>() {
                    @Override
                    public String call(List<Address> addresses) {
                        for (Address address : addresses) {
                            return ofNullable(address.getLocality()).orElse(ofNullable(address.getFeatureName()).orElse("N/A"));
                        }
                        return "N/A";
                    }
                });
    }
}
