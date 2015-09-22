package pl.dariuszbacinski.meteo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

@Getter
@NoArgsConstructor
@Table(name = "Locations", id = "_id")
public class Location extends Model {

    @Column
    private String name = "";
    @Column
    private Integer col = 0;
    @Column
    private Integer row = 0;

}
