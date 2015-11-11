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

    public static class Legend extends DiagramItemViewModel {

        public Legend(String name) {
            super(name, "http://www.meteo.pl/um/metco/leg_um_pl_cbase_256.png");
        }
    }
}
