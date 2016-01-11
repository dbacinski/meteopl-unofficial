package pl.dariuszbacinski.meteo.location.model
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.junit.Rule
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification
import rx.observers.TestSubscriber
import spock.lang.Subject

class MeteoServiceSpec extends ShadowRoboSpecification {

    @Subject
    MeteoService objectUnderTest
    TestSubscriber<Location> subscriber = new TestSubscriber<>()
    @Rule
    MockWebServer server = new MockWebServer()

    void setup() {
        server.enqueue(new MockResponse().setResponseCode(301)
                .setHeader("Location", server.url("um/metco/mgram_pict.php?ntype=0u&row=409&col=248&lang=pl")))
        server.enqueue(new MockResponse())
    }

    def "resolves grid location based on coarse location"() {
        given:
            String baseUrl = server.url("/").toString();
            objectUnderTest = new MeteoService(baseUrl)
            def location = new android.location.Location("")
            location.setLatitude 52.2d
            location.setLongitude 21.0d
        when:
            objectUnderTest.getGridCoordinatedBasedOnLocation(location).subscribe(subscriber)
        then:
            subscriber.awaitTerminalEvent()
            subscriber.assertValue(new Location("", 409, 248, ""))
    }
}
