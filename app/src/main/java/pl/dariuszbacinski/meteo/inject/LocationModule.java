package pl.dariuszbacinski.meteo.inject;

import android.content.Context;

import com.tbruyelle.rxpermissions.RxPermissions;

import dagger.Module;
import dagger.Provides;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import pl.dariuszbacinski.meteo.location.model.LocationNameResolver;
import pl.dariuszbacinski.meteo.location.model.MeteoService;

@Module
public class LocationModule {

    @Provides
    @ActivityScope
    public RxPermissions provideRxPermissions(@ApplicationContext Context context) {
        return RxPermissions.getInstance(context);
    }

    @Provides
    @ActivityScope
    public ReactiveLocationProvider provideReactiveLocationProvider(@ApplicationContext Context context) {
        return new ReactiveLocationProvider(context);
    }

    @Provides
    @ActivityScope
    public MeteoService provideMeteoService() {
        return new MeteoService("http://meteo.pl");
    }

    @Provides
    @ActivityScope
    public LocationNameResolver provideLocationNameResolver(ReactiveLocationProvider reactiveLocationProvider) {
        return new LocationNameResolver(reactiveLocationProvider);
    }
}
