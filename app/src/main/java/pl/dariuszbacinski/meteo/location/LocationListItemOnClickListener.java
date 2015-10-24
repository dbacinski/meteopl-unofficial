package pl.dariuszbacinski.meteo.location;

import android.view.View;
import android.widget.CheckedTextView;

import com.bignerdranch.android.multiselector.MultiSelector;

public class LocationListItemOnClickListener implements View.OnClickListener {

    private MultiSelector multiSelector;
    private int position;

    public LocationListItemOnClickListener(MultiSelector multiSelector, int originalIndex) {
        this.multiSelector = multiSelector;
        this.position = originalIndex;
    }

    @Override
    public void onClick(View v) {
        multiSelector.tapSelection(position, -1);
        ((CheckedTextView) v).toggle();
    }
}
