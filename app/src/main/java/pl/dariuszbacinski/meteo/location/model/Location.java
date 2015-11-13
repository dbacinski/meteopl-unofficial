package pl.dariuszbacinski.meteo.location.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
@EqualsAndHashCode
@Table(name = "Locations", id = "_id")
public class Location extends Model {

    @Column
    private String name;
    @Column
    private Integer row;
    @Column
    private Integer col;
}
