package pl.dariuszbacinski.meteo.location.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Getter
@NoArgsConstructor
//XXX @AllArgsConstructor Dangerous for identical types
@EqualsAndHashCode
@Builder
@Table(name = "Locations", id = "_id")
public class Location extends Model {

    @Column
    private String name;
    @Column
    private Integer row;
    @Column
    private Integer col;

    public Location(String name, Integer row, Integer col) {
        this.name = name;
        this.row = row;
        this.col = col;
    }
}
