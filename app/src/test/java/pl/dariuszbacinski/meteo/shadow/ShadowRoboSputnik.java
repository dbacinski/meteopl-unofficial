package pl.dariuszbacinski.meteo.shadow;

import org.junit.runners.model.InitializationError;
import org.robolectric.internal.bytecode.InstrumentationConfiguration;
import org.robolectric.internal.dependency.DependencyResolver;

import pl.polidea.robospock.internal.RoboSputnik;

public class ShadowRoboSputnik extends RoboSputnik {

    public ShadowRoboSputnik(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    public InstrumentationConfiguration createClassLoaderConfig() {
        return InstrumentationConfiguration.newBuilder()
                .doNotAquireClass(DependencyResolver.class.getName())
//                .addInstrumentedClass(ShadowClass.class.getName())
                .build();
    }


}