package pl.dariuszbacinski.meteo.diagram.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.eccyan.optional.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class SelectedDiagram extends Model {

    @Column
    private Long locationId;

    public Long getLocationId() {
        return locationId;
    }

    public static Optional<SelectedDiagram> loadSingle() {
        //TODO move to repo
        return Optional.ofNullable((SelectedDiagram) new Select().from(SelectedDiagram.class).executeSingle());
    }

    public void saveSingle() {
        ActiveAndroid.beginTransaction();

        try {
            new Delete().from(SelectedDiagram.class).execute();
            save();
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }
}
