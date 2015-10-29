package pl.dariuszbacinski.meteo.info;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsFragment;

import pl.dariuszbacinski.meteo.R;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        LibsFragment fragment = new LibsBuilder()
                .withAboutIconShown(true)
                .withAboutVersionShown(true)
                .withAboutDescription(getString(R.string.info_author))
                .withVersionShown(false)
                .withLicenseShown(true)
                .fragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

    }
}
