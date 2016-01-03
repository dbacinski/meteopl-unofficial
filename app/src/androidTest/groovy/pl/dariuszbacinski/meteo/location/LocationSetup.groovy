package pl.dariuszbacinski.meteo.location

import android.app.Activity
import android.content.Context
import android.location.Address
import android.support.test.espresso.Espresso
import com.google.android.gms.location.LocationRequest
import com.tbruyelle.rxpermissions.RxPermissions
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import pl.dariuszbacinski.meteo.component.LocationListIdlingResource
import pl.dariuszbacinski.meteo.inject.ApplicationModule
import pl.dariuszbacinski.meteo.inject.DaggerApplicationComponent
import pl.dariuszbacinski.meteo.location.model.MockLocation
import pl.dariuszbacinski.meteo.location.view.LocationFragment
import rx.Observable

import static android.Manifest.permission.ACCESS_COARSE_LOCATION
import static org.mockito.BDDMockito.given
import static org.mockito.Matchers.*
import static pl.dariuszbacinski.meteo.location.LocationListFeature.getLocationListAdapter

class LocationSetup {

    LocationListIdlingResource listIdlingResource
    RxPermissions rxPermissionsMock
    ReactiveLocationProvider reactiveLocationProviderMock

    def setupIdlingResource(Activity activity) {
        listIdlingResource = new LocationListIdlingResource(getLocationListAdapter(activity).getLoading())
        Espresso.registerIdlingResources listIdlingResource
    }

    def setupStubs(Context applicationContext) {
        TestLocationComponent locationComponent = DaggerTestLocationComponent.builder().applicationComponent(DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(applicationContext)).build()).testLocationModule(new TestLocationModule()).build()
        LocationFragment.LocationComponentProvider.setTestLocationComponent(locationComponent)
        rxPermissionsMock = locationComponent.rxPermissions()
        reactiveLocationProviderMock = locationComponent.reactiveLocationProvider()
        given rxPermissionsMock.request(ACCESS_COARSE_LOCATION) willReturn Observable.just(true)
        given reactiveLocationProviderMock.getUpdatedLocation(isA(LocationRequest)) willReturn Observable.just(new MockLocation().getMockNetworkProviderLocation())
        given reactiveLocationProviderMock.getReverseGeocodeObservable(anyDouble(), anyDouble(), anyInt()) willReturn Observable.just([] as List<Address>)
    }

    def cleanup() {
        if (listIdlingResource != null) {
            Espresso.unregisterIdlingResources listIdlingResource
        }
    }
}