package pl.dariuszbacinski.meteo.location.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.databinding.ActivityLocationBinding;

public class LocationActivity extends AppCompatActivity {

    private ActivityLocationBinding locationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationBinding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(locationBinding.getRoot());
        setSupportActionBar(locationBinding.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location, menu);
        return true;
    }
}
