package pl.dariuszbacinski.meteo
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import groovy.transform.CompileStatic
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.dariuszbacinski.meteo.location.view.LocationActivity

import static android.support.test.espresso.Espresso.onView
import static android.support.test.espresso.assertion.ViewAssertions.matches
import static android.support.test.espresso.matcher.ViewMatchers.withId
import static android.support.test.espresso.matcher.ViewMatchers.withText

@CompileStatic
@RunWith(AndroidJUnit4.class)
class DiagramTestCase {

    @Rule
    public ActivityTestRule<LocationActivity> activity = [LocationActivity] as ActivityTestRule<LocationActivity>

    @Test
    void alwaysPass() {
        assert true
    }

    @Test
    void filterLocationsList() {
        onView withId(R.id.action_search) perform typeText("Ber")
        sleep(500)
        onView withId(R.id.location_name) check matches(withText("Berlin"))
    }
}