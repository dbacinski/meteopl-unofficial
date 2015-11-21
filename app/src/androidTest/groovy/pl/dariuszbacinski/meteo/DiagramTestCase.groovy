package pl.dariuszbacinski.meteo

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import groovy.transform.CompileStatic
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.dariuszbacinski.meteo.location.view.LocationActivity

@CompileStatic
@RunWith(AndroidJUnit4.class)
class DiagramTestCase {

    @Rule
    public ActivityTestRule<LocationActivity> activity = [LocationActivity] as ActivityTestRule<LocationActivity>

    @Test
    void alwaysPass() {
        assert true
    }


}