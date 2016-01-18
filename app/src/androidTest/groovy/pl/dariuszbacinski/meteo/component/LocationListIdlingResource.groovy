package pl.dariuszbacinski.meteo.component

import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.support.test.espresso.IdlingResource
import groovy.transform.CompileStatic
import hugo.weaving.DebugLog

import java.util.concurrent.atomic.AtomicBoolean

@CompileStatic
class LocationListIdlingResource extends Observable.OnPropertyChangedCallback implements IdlingResource {

    AtomicBoolean isIdle = new AtomicBoolean(true)
    IdlingResource.ResourceCallback callback

    LocationListIdlingResource(ObservableBoolean listLoading) {
        listLoading.addOnPropertyChangedCallback(this)
    }

    @Override
    String getName() {
        return LocationListIdlingResource.name
    }

    void setIsIdle(boolean isIdle) {
        this.isIdle.set(isIdle)
    }

    @DebugLog
    @Override
    boolean isIdleNow() {
        return isIdle.get()
    }

    @Override
    void registerIdleTransitionCallback(IdlingResource.ResourceCallback callback) {
        this.callback = callback
    }

    @DebugLog
    @Override
    void onPropertyChanged(Observable sender, int propertyId) {
        boolean listLoading = ((ObservableBoolean) sender).get()

        if (!listLoading) {
            new Thread(new NotifyToIdle(this)).start()
        }
    }

    static class NotifyToIdle implements Runnable {

        private LocationListIdlingResource listIdlingResource

        NotifyToIdle(LocationListIdlingResource listIdlingResource) {
            this.listIdlingResource = listIdlingResource
        }

        @Override
        void run() {
            waitForListRedraw()
            listIdlingResource.callback.onTransitionToIdle()
            listIdlingResource.isIdle.set(true)
        }

        static waitForListRedraw() {
            sleep 100
        }
    }
}