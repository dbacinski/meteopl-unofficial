package pl.dariuszbacinski.meteo.diagram;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
@Table(name = "Locations", id = "_id")
public class Location extends Model {

    @Column
    private String name;
    @Column
    private Integer col;
    @Column
    private Integer row;
}
