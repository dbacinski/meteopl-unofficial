package pl.dariuszbacinski.meteo;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.dariuszbacinski.meteo.info.InfoActivity;

import static junit.framework.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ApplicationTest {

    @Rule
    public ActivityTestRule<InfoActivity> mActivityRule =
            new ActivityTestRule<>(InfoActivity.class);

    @Test
    public void alwaysPass() {
        assertFalse(false);
    }
}
