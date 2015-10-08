package pl.dariuszbacinski.meteo;

import android.support.multidex.MultiDexApplication;

import com.activeandroid.ActiveAndroid;

public class WeatherApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
