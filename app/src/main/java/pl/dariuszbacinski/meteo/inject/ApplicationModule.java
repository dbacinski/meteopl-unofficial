package pl.dariuszbacinski.meteo.inject;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    Context applicationContext;

    public ApplicationModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides
    @ApplicationContext
    Context provideApplicationContext() {
        return applicationContext;
    }
}
