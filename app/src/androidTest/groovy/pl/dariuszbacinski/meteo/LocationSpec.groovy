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

public class LocationSpec  extends Specification{

    @UseActivity(value=LocationActivity, runMode = METHOD)
    def locationActivity

    def "verify activity"() {
        expect:
            locationActivity instanceof LocationActivity
    }

    def "filter locations"() {
        when: "enter filter query Ber"
            enterQueryToSearchView("Ber")
        then: "location list contains location Berlin"
            sleep(500)
            listContainsLocation("Berlin")
    }

    private ViewInteraction enterQueryToSearchView(String berlin_query) {
        onView withId(R.id.action_search) perform typeText(berlin_query)
    }

    private ViewInteraction listContainsLocation(String locationName) {
        onView withId(R.id.location_name) check matches(withText(locationName))
    }
}
