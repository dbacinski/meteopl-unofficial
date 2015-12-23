package pl.dariuszbacinski.meteo.inject;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context applicationContext();
}
