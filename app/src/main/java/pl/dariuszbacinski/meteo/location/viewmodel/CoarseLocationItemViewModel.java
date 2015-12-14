package pl.dariuszbacinski.meteo.location.viewmodel;

import android.databinding.Bindable;
import android.support.annotation.DrawableRes;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import pl.dariuszbacinski.meteo.BR;

@Parcel
public class CoarseLocationItemViewModel extends LocationItemViewModel {
    @DrawableRes
    int icon;

    @ParcelConstructor
    public CoarseLocationItemViewModel(String name, boolean checked, int icon) {
        super(name, checked);
        this.icon = icon;
    }

    @Bindable
    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
        notifyPropertyChanged(BR.icon);
    }
}
