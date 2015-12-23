package pl.dariuszbacinski.meteo.inject;

import dagger.Component;
import pl.dariuszbacinski.meteo.location.model.CoarseLocation;
import pl.dariuszbacinski.meteo.location.view.LocationFragment;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = LocationModule.class)
public interface LocationComponent {

    void inject(LocationFragment locationFragment);

    CoarseLocation coarseLocation();
}
