package pl.dariuszbacinski.meteo.diagram.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import pl.dariuszbacinski.meteo.info.InfoActivity;
import pl.dariuszbacinski.meteo.location.view.LocationActivity;

public class DiagramViewModel {

    private Activity activity;

    public DiagramViewModel(Activity activity) {
        this.activity = activity;
    }

    public void startLocationActivityWhenNoFavoriteLocations(int numberOfFavoriteLocations) {
        if (numberOfFavoriteLocations == 0) {
            startLocationActivity();
            activity.finish();
        }
    }


    public void startLocationActivity(View view) {
        startLocationActivity();
    }

    public void startLocationActivity() {
        activity.startActivity(new Intent(activity, LocationActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }


    public void startInfoActivity() {
        activity.startActivity(new Intent(activity, InfoActivity.class));
    }
}
