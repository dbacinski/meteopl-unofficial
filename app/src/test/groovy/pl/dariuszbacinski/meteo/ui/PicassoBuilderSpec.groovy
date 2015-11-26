package pl.dariuszbacinski.meteo.ui

import com.squareup.picasso.Picasso
import org.robolectric.RuntimeEnvironment
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification

class PicassoBuilderSpec extends ShadowRoboSpecification {

    def "creates Picasso without network cache"() {
        given:
            PicassoBuilder objectUnderTest = new PicassoBuilder(RuntimeEnvironment.application)
        when:
            Picasso picasso = objectUnderTest.build();
        then:
            picasso != null
    }
}
