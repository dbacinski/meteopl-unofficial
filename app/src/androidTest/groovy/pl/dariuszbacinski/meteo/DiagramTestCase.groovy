package pl.dariuszbacinski.meteo

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.dariuszbacinski.meteo.diagram.view.DiagramActivity

import static junit.framework.Assert.assertFalse

@RunWith(AndroidJUnit4.class)
public class DiagramTestCase {

    @Rule
    public final ActivityTestRule<DiagramActivity> main = new ActivityTestRule<>(DiagramActivity.class)

    @Test
    public void alwaysPass(){
        assertFalse(false);
    }
}