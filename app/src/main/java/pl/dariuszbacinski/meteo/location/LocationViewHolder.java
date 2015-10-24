package pl.dariuszbacinski.meteo.location;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckedTextView;

import pl.dariuszbacinski.meteo.databinding.ListItemLocationBinding;

class LocationViewHolder extends RecyclerView.ViewHolder {
    private ListItemLocationBinding binding;

    public LocationViewHolder(CheckedTextView checkedTextView) {
        super(checkedTextView);
        binding = DataBindingUtil.bind(checkedTextView);
    }

    public ListItemLocationBinding getBinding() {
        return binding;
    }
}
