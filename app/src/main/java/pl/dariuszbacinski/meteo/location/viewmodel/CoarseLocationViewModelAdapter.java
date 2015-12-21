package pl.dariuszbacinski.meteo.location.viewmodel;

import android.content.Context;

import com.eccyan.optional.Optional;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import lombok.Getter;
import lombok.Setter;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.location.model.CoarseLocation;
import pl.dariuszbacinski.meteo.location.model.FavoriteLocationRepository;
import pl.dariuszbacinski.meteo.location.model.Location;
import pl.dariuszbacinski.meteo.location.model.LocationNameResolver;
import pl.dariuszbacinski.meteo.location.model.LocationRepository;
import pl.dariuszbacinski.meteo.location.model.MeteoService;
import pl.dariuszbacinski.meteo.location.model.MockLocation;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;


//TODO add tests
@Parcel
@Getter
@Setter
public class CoarseLocationViewModelAdapter {

    public static final Long COARSE_LOCATION_ID = 999999L;
    CoarseLocationItemViewModel locationItemViewModel;
    Location location;

    @DebugLog
    public Subscription requestLocation(Context context) {
        final String errorString = context.getString(R.string.location_gps_error);
        ReactiveLocationProvider reactiveLocationProvider = new ReactiveLocationProvider(context);
        CoarseLocation coarseLocation = new CoarseLocation(reactiveLocationProvider, RxPermissions.getInstance(context), new MeteoService("http://www.meteo.pl"), new LocationNameResolver(reactiveLocationProvider));
        return coarseLocation.requestLocation().startWith(getStoredCoarseLocationObservable()).subscribe(new Subscriber<Location>() {

            @Override
            public void onNext(Location location) {
                locationItemViewModel.setChecked(isFavoriteLocation(location));
                locationItemViewModel.setName(location.getName());
                locationItemViewModel.setIcon(R.drawable.ic_gps_fixed);
                location.setId(COARSE_LOCATION_ID);
                CoarseLocationViewModelAdapter.this.location = location;
            }

            @Override
            public void onError(Throwable e) {
                showErrorMessage(errorString);
            }

            @Override
            public void onCompleted() {
            }
        });
    }

    private boolean isFavoriteLocation(Location location) {
        return new FavoriteLocationRepository().findAll().contains(location);
    }

    private void showErrorMessage(String errorString) {
        Optional<String> name = getStoredCoarseLocation().map(new Func1<Location, String>() {
            @Override
            public String call(Location location) {
                return location.getName();
            }
        });

        locationItemViewModel.setName(name.orElse(errorString));
        locationItemViewModel.setIcon(R.drawable.ic_gps_off);
        //TODO show snack bar
    }

    public Observable<Location> getStoredCoarseLocationObservable() {
        return getStoredCoarseLocation().toObservable();
    }

    private Optional<Location> getStoredCoarseLocation() {
        if (location != null) {
            return Optional.of(location);
        } else {
            return new LocationRepository().findOne(COARSE_LOCATION_ID);
        }
    }

    public List<Location> getSelectedCoarseLocation() {
        if (locationItemViewModel.isChecked()) {
            return getStoredCoarseLocationObservable().toList().toBlocking().single();
        } else {
            return new ArrayList<>();
        }
    }

    public Subscription mockLocation(Context context) {
        return new MockLocation(new ReactiveLocationProvider(context)).mockNetworkProvider().subscribe();
    }
}
