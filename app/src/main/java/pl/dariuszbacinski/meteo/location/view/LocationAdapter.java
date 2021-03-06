package pl.dariuszbacinski.meteo.location.view;

import android.databinding.ObservableBoolean;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import pl.dariuszbacinski.meteo.databinding.ListItemLocationBinding;
import pl.dariuszbacinski.meteo.location.model.Location;
import pl.dariuszbacinski.meteo.location.viewmodel.LocationItemViewModel;
import pl.dariuszbacinski.meteo.location.viewmodel.LocationListViewModel;

@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PRIVATE)
public class LocationAdapter extends RecyclerView.Adapter<LocationViewHolder> {

    private LocationListViewModel locationListViewModel;
    @Getter(AccessLevel.PUBLIC)
    private ObservableBoolean loading = new ObservableBoolean(false);

    public LocationAdapter(LocationListViewModel locationListViewModel) {
        this.locationListViewModel = locationListViewModel;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemLocationBinding binding = ListItemLocationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LocationViewHolder((CheckedTextView) binding.getRoot());
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        LocationItemViewModel itemViewModel = locationListViewModel.getItemViewModel(position);
        holder.getBinding().setItem(itemViewModel);
        holder.getBinding().setListener(locationListViewModel.getOnClickListener(position));
    }

    @Override
    public int getItemCount() {
        return locationListViewModel.getItemCount();
    }

    public void filterLocationsByName(final String name) {
        loading.set(true);
        filterLocationsAndNotify(name);
        loading.set(false);
    }

    private void filterLocationsAndNotify(String name) {
        locationListViewModel.filterLocationsByName(name);
        notifyDataSetChanged();
    }

    public List<Location> getSelectedLocations() {
        return locationListViewModel.getSelectedLocations();
    }
}
