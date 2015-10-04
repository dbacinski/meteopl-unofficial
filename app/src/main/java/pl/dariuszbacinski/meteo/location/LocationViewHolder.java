package pl.dariuszbacinski.meteo.location;

import android.view.View;
import android.widget.CheckedTextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

class LocationViewHolder extends SwappingHolder
        implements View.OnClickListener {
    private final CheckedTextView checkedTextView;
    private MultiSelector multiSelector;

    public LocationViewHolder(CheckedTextView checkedTextView, MultiSelector multiSelector) {
        super(checkedTextView, multiSelector);
        this.checkedTextView = checkedTextView;
        this.multiSelector = multiSelector;
        checkedTextView.setOnClickListener(this);
    }

    public void bindName(String name) {
        checkedTextView.setText(name);
    }

    @Override
    public void onClick(View v) {
        multiSelector.tapSelection(this);
        ((CheckedTextView) v).toggle();
    }

    public void bindSelected(boolean selected) {
        checkedTextView.setChecked(selected);
    }
}
