package pl.dariuszbacinski.meteo.view;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

public class SnackbarLightBuilder {
    public Snackbar make(View view, int resId, int duration) {
        final Snackbar snackbar = Snackbar.make(view, resId, duration);
        ((TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
        return snackbar;
    }
}
