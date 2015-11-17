package pl.dariuszbacinski.meteo.ui
import com.squareup.okhttp.Cache
import com.squareup.picasso.Picasso
import org.robolectric.RuntimeEnvironment
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification
import spock.lang.Ignore

class PicassoBuilderSpec extends ShadowRoboSpecification {


    @Ignore(value = "cannot stub final Cache")
    def "creates Picasso with network cache"() {
        given:
            PicassoBuilder objectUnderTest = new PicassoBuilder(RuntimeEnvironment.application)
        when:
            Picasso picasso = objectUnderTest.withNetworkCache(Stub(Cache)).build();
        then:
            picasso != null
    }

    def "creates Picasso without network cache"() {
        given:
            PicassoBuilder objectUnderTest = new PicassoBuilder(RuntimeEnvironment.application)
        when:
            Picasso picasso = objectUnderTest.build();
        then:
            picasso != null
    }
}
