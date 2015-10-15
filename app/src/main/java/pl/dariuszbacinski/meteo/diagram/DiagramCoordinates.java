package pl.dariuszbacinski.meteo.diagram;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.Builder;

@AllArgsConstructor(suppressConstructorProperties = true)
@Value
@Builder
public class DiagramCoordinates implements Parcelable {

    private Integer col;
    private Integer row;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.col);
        dest.writeValue(this.row);
    }

    protected DiagramCoordinates(Parcel in) {
        this.col = (Integer) in.readValue(Integer.class.getClassLoader());
        this.row = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<DiagramCoordinates> CREATOR = new Creator<DiagramCoordinates>() {
        public DiagramCoordinates createFromParcel(Parcel source) {
            return new DiagramCoordinates(source);
        }

        public DiagramCoordinates[] newArray(int size) {
            return new DiagramCoordinates[size];
        }
    };
}
