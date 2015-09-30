package pl.dariuszbacinski.meteo.location;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.dariuszbacinski.meteo.diagram.Location;

@Getter
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
@Table(name = "FavoriteLocations", id = "_id")
public class FavoriteLocation extends Model {

    @Column
    private Location location;
}
