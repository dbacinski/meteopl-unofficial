package pl.dariuszbacinski.meteo.location;

import android.app.Activity;
import android.os.Bundle;

import pl.dariuszbacinski.meteo.R;

public class LocationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        throw new RuntimeException();
    }
}
