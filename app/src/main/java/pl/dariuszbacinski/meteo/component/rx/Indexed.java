package pl.dariuszbacinski.meteo.component.rx;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@AllArgsConstructor(suppressConstructorProperties = true)
@Getter
public class Indexed<T> {
    @Setter
    private int index;
    private int originalIndex;
    private T value;
}
