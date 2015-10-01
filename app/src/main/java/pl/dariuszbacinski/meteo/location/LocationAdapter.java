package pl.dariuszbacinski.meteo.location;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.bignerdranch.android.multiselector.MultiSelector;

import pl.dariuszbacinski.meteo.diagram.Location;

public class LocationAdapter extends RecyclerView.Adapter {
    private FavoriteLocationRepository favoriteLocationRepository;
    private MultiSelector multiSelector = new MultiSelector();

    public LocationAdapter(FavoriteLocationRepository favoriteLocationRepository) {
        this.favoriteLocationRepository = favoriteLocationRepository;
        multiSelector.setSelectable(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
        return new LocationViewHolder((CheckedTextView) view, multiSelector);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Location location = favoriteLocationRepository.findAll().get(position).getLocation();
        ((LocationViewHolder) holder).bindName(location.getName());
    }

    @Override
    public int getItemCount() {
        return favoriteLocationRepository.findAll().size();
    }
}
