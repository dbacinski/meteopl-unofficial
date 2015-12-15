package pl.dariuszbacinski.meteo.location.model;

import android.location.LocationManager;
import android.os.Build;
import android.os.SystemClock;

import com.google.android.gms.common.api.Status;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;

public class MockLocation {

    private ReactiveLocationProvider reactiveLocationProvider;

    public MockLocation(ReactiveLocationProvider reactiveLocationProvider) {
        this.reactiveLocationProvider = reactiveLocationProvider;
    }

    private android.location.Location getMockNetworkProviderLocation() {
        android.location.Location mockLocation = new android.location.Location(LocationManager.NETWORK_PROVIDER);
        mockLocation.setLatitude(52.22977);
        mockLocation.setLongitude(21.01178);
        mockLocation.setAccuracy(1.0f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mockLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        }
        mockLocation.setTime(System.currentTimeMillis());
        return mockLocation;
    }

    public Observable<Status> mockNetworkProvider() {
            return reactiveLocationProvider.mockLocation(Observable.just(getMockNetworkProviderLocation()));
    }
}
