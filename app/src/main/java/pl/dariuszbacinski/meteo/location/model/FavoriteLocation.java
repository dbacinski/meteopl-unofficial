package pl.dariuszbacinski.meteo.location.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
@Table(name = "FavoriteLocations", id = "_id")
public class FavoriteLocation extends Model {

    @Column
    private Location location;
}
