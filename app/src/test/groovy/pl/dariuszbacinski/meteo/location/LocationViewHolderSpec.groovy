package pl.dariuszbacinski.meteo.location

import android.widget.CheckedTextView
import com.bignerdranch.android.multiselector.MultiSelector
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Config(manifest = "src/main/AndroidManifest.xml", shadows = ShadowSwappingHolder)
class LocationViewHolderSpec extends ShadowRoboSpecification {


    def "toggles view when clicked"() {
        given:
            CheckedTextView checkedTextView = Spy(CheckedTextView, constructorArgs: [RuntimeEnvironment.application])
            LocationViewHolder objectUnderTest = new LocationViewHolder(checkedTextView, new MultiSelector())
        when:
            objectUnderTest.onClick(checkedTextView)
        then:
            checkedTextView.toggle()
    }

    def "item is selected in multi selector when clicked"() {
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
