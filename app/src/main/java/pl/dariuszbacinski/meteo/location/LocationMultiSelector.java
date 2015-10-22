package pl.dariuszbacinski.meteo.location;

import com.bignerdranch.android.multiselector.MultiSelector;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Delegate;
import pl.dariuszbacinski.meteo.rx.Indexed;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

@AllArgsConstructor(suppressConstructorProperties = true)
public class LocationMultiSelector {
    @Delegate
    MultiSelector multiSelector;

    public void restoreSelectedItems(Observable<Indexed<Location>> locationObservable, final List<Location> selectedLocations) {

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
