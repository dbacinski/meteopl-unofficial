package pl.dariuszbacinski.meteo;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.activeandroid.ActiveAndroid;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.core.CrashlyticsCore;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import io.fabric.sdk.android.Fabric;

public class WeatherApplication extends MultiDexApplication {

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        refWatcher = LeakCanary.install(this);
        initReleaseMode();
    }

    private void initReleaseMode() {
        if(!BuildConfig.DEBUG) {
            Fabric.with(this, new CrashlyticsCore(), new Answers());
        }
    }

    public static RefWatcher getRefWatcher(Context context) {
        WeatherApplication application = (WeatherApplication) context.getApplicationContext();
        return application.refWatcher;
    }
}
