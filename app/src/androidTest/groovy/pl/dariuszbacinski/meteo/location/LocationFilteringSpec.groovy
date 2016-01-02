package pl.dariuszbacinski.meteo.location

import android.content.Context
import android.location.Address
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.rule.ActivityTestRule
import com.google.android.gms.location.LocationRequest
import com.tbruyelle.rxpermissions.RxPermissions
import dagger.Component
import dagger.Module
import dagger.Provides
import org.junit.Rule
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import pl.dariuszbacinski.meteo.component.LocationListIdlingResource
import pl.dariuszbacinski.meteo.inject.*
import pl.dariuszbacinski.meteo.location.model.LocationNameResolver
import pl.dariuszbacinski.meteo.location.model.MeteoService
import pl.dariuszbacinski.meteo.location.model.MockLocation
import pl.dariuszbacinski.meteo.location.view.LocationActivity
import pl.dariuszbacinski.meteo.location.view.LocationFragment
import rx.Observable
import spock.lang.Specification

import static android.Manifest.permission.ACCESS_COARSE_LOCATION
import static org.mockito.BDDMockito.given
import static org.mockito.Matchers.*
import static org.mockito.Mockito.mock
import static pl.dariuszbacinski.meteo.location.LocationListFeature.*

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = TestModule.class)
public interface TestLocationComponent extends LocationComponent {
    RxPermissions rxPermissions()
    ReactiveLocationProvider reactiveLocationProvider()
}

@Module
public class TestModule {

    @Provides
    @ActivityScope
    public RxPermissions provideRxPermissions(@ApplicationContext Context context) {
        return mock(RxPermissions)
    }

    @Provides
    @ActivityScope
    public ReactiveLocationProvider provideReactiveLocationProvider(
            @ApplicationContext Context context) {
        return mock(ReactiveLocationProvider)
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

public class LocationFilteringSpec extends Specification {

    @Rule
    ActivityTestRule<LocationActivity> locationActivityRule = new ActivityTestRule(LocationActivity)
    LocationListIdlingResource listIdlingResource
    RxPermissions rxPermissionsMock
    ReactiveLocationProvider reactiveLocationProviderMock

    def setup() {
        listIdlingResource = new LocationListIdlingResource(getLocationListAdapter(locationActivityRule.getActivity()).getLoading())
        Espresso.registerIdlingResources listIdlingResource
        initDagger();
    }

    Object initDagger() {
        Context applicationContext = InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        TestLocationComponent locationComponent = DaggerTestLocationComponent.builder().applicationComponent(DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(applicationContext)).build()).testModule(new TestModule()).build()
        LocationFragment.LocationComponentProvider.setTestLocationComponent(locationComponent)
        rxPermissionsMock = locationComponent.rxPermissions()
        reactiveLocationProviderMock = locationComponent.reactiveLocationProvider()
        given rxPermissionsMock.request(ACCESS_COARSE_LOCATION) willReturn Observable.just(true)
        given reactiveLocationProviderMock.getUpdatedLocation(isA(LocationRequest)) willReturn Observable.just(new MockLocation().getMockNetworkProviderLocation())
        given reactiveLocationProviderMock.getReverseGeocodeObservable(anyDouble(), anyDouble(), anyInt()) willReturn Observable.just([] as List<Address>)
    }

    def cleanup() {
        Espresso.unregisterIdlingResources listIdlingResource
    }

    def "filter locations by prefix"() {
        when: "enter filter query Ber"
            filterLocationsWithQuery "Ber", listIdlingResource
        then: "location list contains location Berlin"
            listContainsLocation "Berlin"
    }

    def "filter locations by containing string"() {
        when: "enter filter query arsza"
            filterLocationsWithQuery "arsza", listIdlingResource
        then: "location list contains location Warszawa"
            listContainsLocation "Warszawa"
    }

    def "filter locations by suffix"() {
        when: "enter filter query belin"
            filterLocationsWithQuery "belin", listIdlingResource
        then: "location list contains location Izabelin"
            listContainsLocation "Izabelin"
    }
}
