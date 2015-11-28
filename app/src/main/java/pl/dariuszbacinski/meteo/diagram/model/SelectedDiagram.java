package pl.dariuszbacinski.meteo.diagram.model;

import android.support.annotation.VisibleForTesting;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.eccyan.optional.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static lombok.AccessLevel.PACKAGE;

@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class SelectedDiagram extends Model {

    @VisibleForTesting
    @Setter(PACKAGE)
    private static SelectedDiagramRepository selectedDiagramRepository = new SelectedDiagramRepository();
    @Column
    private Long locationId;

    public Long getLocationId() {
        return locationId;
    }

    public static Optional<SelectedDiagram> loadSingle() {
        return selectedDiagramRepository.findOne();
    }

    public void saveSingle() {
        selectedDiagramRepository.saveOne(this);
    }
}
