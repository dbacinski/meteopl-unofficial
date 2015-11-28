package pl.dariuszbacinski.meteo.diagram.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.eccyan.optional.Optional;

public class SelectedDiagramRepository {

    public Optional<SelectedDiagram> findOne() {
        return Optional.ofNullable((SelectedDiagram) new Select().from(SelectedDiagram.class).executeSingle());
    }

    public void saveOne(SelectedDiagram selectedDiagram) {
        ActiveAndroid.beginTransaction();

        try {
            new Delete().from(SelectedDiagram.class).execute();
            selectedDiagram.save();
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }
}
