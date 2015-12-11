package pl.dariuszbacinski.meteo.location.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


@Getter
@NoArgsConstructor
//XXX @AllArgsConstructor is dangerous for identical types
@EqualsAndHashCode(callSuper = false)
@Builder
@ToString
@Table(name = "Locations", id = "_id")
public class Location extends Model {

    @Accessors(chain = true)
    @Setter
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
