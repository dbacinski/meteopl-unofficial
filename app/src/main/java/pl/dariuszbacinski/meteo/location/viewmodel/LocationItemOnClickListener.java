package pl.dariuszbacinski.meteo.location.viewmodel;

import android.view.View;
import android.widget.CheckedTextView;

import com.bignerdranch.android.multiselector.MultiSelector;

public class LocationItemOnClickListener implements View.OnClickListener {

    private MultiSelector multiSelector;
    private int position;

    public LocationItemOnClickListener(MultiSelector multiSelector, int originalIndex) {
        this.multiSelector = multiSelector;
        this.position = originalIndex;
    }

    @Override
    public void onClick(View v) {
        multiSelector.tapSelection(position, -1);
        ((CheckedTextView) v).toggle();
    }
}
