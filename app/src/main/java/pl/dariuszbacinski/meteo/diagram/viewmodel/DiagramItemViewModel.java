package pl.dariuszbacinski.meteo.diagram.viewmodel;

import android.databinding.BaseObservable;

import lombok.AllArgsConstructor;

@AllArgsConstructor(suppressConstructorProperties = true)
public class DiagramItemViewModel extends BaseObservable {
    String title;
    String imageUrl;

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
