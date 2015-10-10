package pl.dariuszbacinski.meteo.location
import android.widget.CheckedTextView
import com.bignerdranch.android.multiselector.MultiSelector
import org.robolectric.RuntimeEnvironment
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification

class LocationViewHolderSpec extends ShadowRoboSpecification {


    def "toggles view on click"() {
        given:
            CheckedTextView checkedTextView = Spy(CheckedTextView, constructorArgs: [RuntimeEnvironment.application])
            LocationViewHolder objectUnderTest = new LocationViewHolder(checkedTextView, new MultiSelector())
        when:
            objectUnderTest.onClick(checkedTextView)
        then:
            checkedTextView.toggle()
    }

    def "item is selected in multi selector on click"() {
        given:
            MultiSelector multiSelector = new MultiSelector()
            multiSelector.setSelectable(true)
            CheckedTextView checkedTextView = Spy(CheckedTextView, constructorArgs: [RuntimeEnvironment.application])
            LocationViewHolder objectUnderTest = new LocationViewHolder(checkedTextView, multiSelector)
        when:
            objectUnderTest.onClick(checkedTextView)
        then:
            multiSelector.getSelectedPositions().size() == 1;
    }
}
