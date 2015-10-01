package pl.dariuszbacinski.meteo.location
import com.bignerdranch.android.multiselector.SwappingHolder
import org.junit.Ignore
import org.junit.runner.RunWith
import org.junit.runners.model.InitializationError
import org.robolectric.internal.bytecode.InstrumentationConfiguration
import org.robolectric.internal.dependency.DependencyResolver
import pl.polidea.robospock.internal.RoboSputnik
import spock.lang.Specification

@Ignore
@RunWith(ShadowRoboSputnik)
class ShadowRoboSpecification extends Specification {

    static class ShadowRoboSputnik extends RoboSputnik {

        ShadowRoboSputnik(Class<?> clazz) throws InitializationError {
            super(clazz)
        }

        @Override
        InstrumentationConfiguration createClassLoaderConfig() {
            return InstrumentationConfiguration.newBuilder()
                    .doNotAquireClass(DependencyResolver.class.getName())
                    .addInstrumentedClass(SwappingHolder.class.getName())
                    .build();
        }
    }
}