package pl.dariuszbacinski.meteo.diagram
import groovy.transform.CompileStatic

import static android.support.test.espresso.Espresso.onView
import static android.support.test.espresso.assertion.ViewAssertions.matches
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import static android.support.test.espresso.matcher.ViewMatchers.withText

@CompileStatic
public class DiagramFeature {

    static boolean tabWithNameIsLoaded(String name) {
        onView withText(name) check matches(isDisplayed())
        return true
    }
}