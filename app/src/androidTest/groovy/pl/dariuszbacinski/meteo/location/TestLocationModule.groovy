package pl.dariuszbacinski.meteo.location

import android.content.Context
import com.tbruyelle.rxpermissions.RxPermissions
import dagger.Module
import dagger.Provides
import org.mockito.Mockito
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import pl.dariuszbacinski.meteo.inject.ActivityScope
import pl.dariuszbacinski.meteo.inject.ApplicationContext
import pl.dariuszbacinski.meteo.location.model.LocationNameResolver
import pl.dariuszbacinski.meteo.location.model.MeteoService

@Module
public class TestLocationModule {

    @Provides
    @ActivityScope
    public RxPermissions provideRxPermissions(@ApplicationContext Context context) {
        return Mockito.mock(RxPermissions)
    }

    @Provides
    @ActivityScope
    public ReactiveLocationProvider provideReactiveLocationProvider(
            @ApplicationContext Context context) {
        return Mockito.mock(ReactiveLocationProvider)
    }

    @Provides
    @ActivityScope
    public MeteoService provideMeteoService() {
        return new MeteoService("http://meteo.pl")
    }

    @Provides
    @ActivityScope
    public LocationNameResolver provideLocationNameResolver(ReactiveLocationProvider reactiveLocationProvider) {
        return new LocationNameResolver(reactiveLocationProvider)
    }
}
