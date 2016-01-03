package pl.dariuszbacinski.meteo.location

import com.tbruyelle.rxpermissions.RxPermissions
import dagger.Component
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import pl.dariuszbacinski.meteo.inject.ActivityScope
import pl.dariuszbacinski.meteo.inject.ApplicationComponent
import pl.dariuszbacinski.meteo.inject.LocationComponent

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = TestLocationModule.class)
public interface TestLocationComponent extends LocationComponent {
    RxPermissions rxPermissions()

    ReactiveLocationProvider reactiveLocationProvider()
}
