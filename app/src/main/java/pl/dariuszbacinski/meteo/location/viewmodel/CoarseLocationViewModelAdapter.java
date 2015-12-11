package pl.dariuszbacinski.meteo.location.viewmodel;

import android.content.Context;
import android.util.Log;

import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import lombok.Getter;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.location.model.CoarseLocation;
import pl.dariuszbacinski.meteo.location.model.FavoriteLocationRepository;
import pl.dariuszbacinski.meteo.location.model.Location;
import pl.dariuszbacinski.meteo.location.model.LocationRepository;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class CoarseLocationViewModelAdapter {

    public static final Long COARSE_LOCATION_ID = 999999L;
    @Getter
    private CoarseLocationItemViewModel locationItemViewModel;
    @Getter
    private Location location;

    public CoarseLocationViewModelAdapter(CoarseLocationItemViewModel initialViewModel) {
        locationItemViewModel = initialViewModel;
    }

    @DebugLog
    public Subscription requestLocation(Context context) {
        final String errorString = context.getString(R.string.location_gps_error);
        CoarseLocation coarseLocation = new CoarseLocation(new ReactiveLocationProvider(context), RxPermissions.getInstance(context));

        return coarseLocation.requestLocation().startWith(getStoredCoarseLocation()).subscribe(new Subscriber<Location>() {

            @Override
            public void onNext(Location location) {
                Log.i("CoarseLocationViewModelAdapter", "onNext: " + location);
                CoarseLocationViewModelAdapter.this.location = location;
                locationItemViewModel.setChecked(isFavoriteLocation(location));
                locationItemViewModel.setName(location.getName());
                locationItemViewModel.setIcon(R.drawable.ic_gps_fixed);
                //TODO store GPS location
                location.setId(COARSE_LOCATION_ID);
                location.save();
            }

            @DebugLog
            @Override
            public void onError(Throwable e) {
                Log.w("CoarseLocationViewModelAdapter", "onError: ", e);
                showErrorMessage(errorString);
            }

            @DebugLog
            @Override
            public void onCompleted() {
                Log.i("CoarseLocationViewModelAdapter", "onCompleted:");
            }
        });
    }

    private boolean isFavoriteLocation(Location location) {
        return new FavoriteLocationRepository().findAll().contains(location);
    }

    private void showErrorMessage(String errorString) {
        locationItemViewModel.setName(errorString);
        locationItemViewModel.setIcon(R.drawable.ic_gps_off);
    }

    public Observable<Location> getStoredCoarseLocation() {
        if (location != null) {
            return Observable.just(location);
        } else {
            return new LocationRepository().findOne(COARSE_LOCATION_ID).toObservable();
        }
    }

    public List<Location> getSelectedCoarseLocation() {
        if (locationItemViewModel.isChecked()) {
            return getStoredCoarseLocation().toList().toBlocking().single();
        } else {
            return new ArrayList<>();
        }
    }
}
