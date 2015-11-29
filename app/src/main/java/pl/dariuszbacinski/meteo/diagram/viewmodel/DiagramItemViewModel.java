package pl.dariuszbacinski.meteo.diagram.viewmodel;

import android.databinding.BaseObservable;

import lombok.Builder;


@Builder
public class DiagramItemViewModel extends BaseObservable {
    private String title;
    private String imageUrl;
    private Long id;

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static class Legend extends DiagramItemViewModel {

        public static final long DIAGRAM_ID = -1L;

        public Legend(String name) {
            super(name, "http://www.meteo.pl/um/metco/leg_um_pl_cbase_256.png", DIAGRAM_ID);
        }
    }
}
