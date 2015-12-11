package pl.dariuszbacinski.meteo.location.viewmodel;

import android.databinding.Bindable;
import android.support.annotation.DrawableRes;

import pl.dariuszbacinski.meteo.BR;

public class CoarseLocationItemViewModel extends LocationItemViewModel {
    @DrawableRes
    int icon;

    public CoarseLocationItemViewModel(String name, boolean checked, int iconRes) {
        super(name, checked);
        this.icon = iconRes;
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
