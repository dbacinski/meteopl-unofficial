package pl.dariuszbacinski.meteo.location.viewmodel;

import com.bignerdranch.android.multiselector.MultiSelector;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import pl.dariuszbacinski.meteo.component.rx.Indexed;
import pl.dariuszbacinski.meteo.location.model.IndexedLocation;
import pl.dariuszbacinski.meteo.location.model.Location;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

@AllArgsConstructor(suppressConstructorProperties = true)
public class LocationMultiSelector {
    @Delegate
    MultiSelector multiSelector;

    public void restoreSelectedItems(Observable<IndexedLocation> locationObservable, final List<Location> selectedLocations) {

        locationObservable.filter(new Func1<Indexed<Location>, Boolean>() {
            @Override
            public Boolean call(Indexed<Location> locationIndexed) {
                return selectedLocations.contains(locationIndexed.getValue());
            }
        }).forEach(new Action1<Indexed<Location>>() {
            @Override
            public void call(Indexed<Location> locationIndexed) {
                multiSelector.setSelected(locationIndexed.getOriginalIndex(), -1, true);
            }
        });
    }
}
