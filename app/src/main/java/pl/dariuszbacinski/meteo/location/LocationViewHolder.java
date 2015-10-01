package pl.dariuszbacinski.meteo.location;

import android.view.View;
import android.widget.CheckedTextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

class LocationViewHolder extends SwappingHolder
        implements View.OnClickListener {
    private final CheckedTextView textView;
    private MultiSelector multiSelector;

    public LocationViewHolder(CheckedTextView textView, MultiSelector multiSelector) {
        super(textView, multiSelector);
        this.textView = textView;
        this.multiSelector = multiSelector;
        textView.setOnClickListener(this);
    }

    public void bindName(String name) {
        textView.setText(name);
    }

    @Override
    public void onClick(View v) {
        multiSelector.tapSelection(this);
        ((CheckedTextView) v).toggle();
    }
}
