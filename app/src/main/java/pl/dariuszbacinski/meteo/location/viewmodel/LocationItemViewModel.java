package pl.dariuszbacinski.meteo.location.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import pl.dariuszbacinski.meteo.BR;

@AllArgsConstructor(suppressConstructorProperties = true)
@EqualsAndHashCode(callSuper = false)
public class LocationItemViewModel extends BaseObservable {
    String name;
    boolean checked;

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        notifyPropertyChanged(BR.checked);
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public boolean isChecked() {
        return checked;
    }
}
