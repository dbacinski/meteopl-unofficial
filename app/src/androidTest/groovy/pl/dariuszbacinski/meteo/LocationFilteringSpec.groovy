package pl.dariuszbacinski.meteo
import android.support.test.espresso.ViewInteraction
import com.andrewreitz.spock.android.UseActivity
import pl.dariuszbacinski.meteo.location.view.LocationActivity
import spock.lang.Specification

import static android.support.test.espresso.Espresso.onView
import static android.support.test.espresso.action.ViewActions.typeText
import static android.support.test.espresso.assertion.ViewAssertions.matches
import static android.support.test.espresso.matcher.ViewMatchers.withId
import static android.support.test.espresso.matcher.ViewMatchers.withText
import static com.andrewreitz.spock.android.RunMode.METHOD

public class LocationFilteringSpec extends Specification{

    @UseActivity(value=LocationActivity, runMode = METHOD)
    def locationActivity

    def "verify activity"() {
        expect:
            locationActivity instanceof LocationActivity
    }

    def "filter locations by prefix"() {
        when: "enter filter query Ber"
            enterQueryToSearchView("Ber")
            sleep(500)
        then: "location list contains location Berlin"
            listContainsLocation("Berlin")
    }

    def "filter locations by containing string"() {
        when: "enter filter query arsza"
            enterQueryToSearchView("arsza")
            sleep(500)
        then: "location list contains location Warszawa"
            listContainsLocation("Warszawa")
    }

    def "filter locations by suffix"() {
        when: "enter filter query belin"
            enterQueryToSearchView("belin")
            sleep(500)
        then: "location list contains location Izabelin"
            listContainsLocation("Izabelin")
    }

    private ViewInteraction enterQueryToSearchView(String berlin_query) {
        onView withId(R.id.action_search) perform typeText(berlin_query)
    }

    private ViewInteraction listContainsLocation(String locationName) {
        onView withId(R.id.location_name) check matches(withText(locationName))
    }
}
