package pl.dariuszbacinski.meteo.location.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

@AllArgsConstructor(suppressConstructorProperties = true)
@Value
@EqualsAndHashCode(callSuper = true)
public class LocationItemViewModel extends BaseObservable {
    String name;
    boolean checked;

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public boolean isChecked() {
        return checked;
    }
}
