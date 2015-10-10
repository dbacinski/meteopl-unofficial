package pl.dariuszbacinski.meteo.shadow

import android.view.View
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import org.robolectric.annotation.Implements;

@Implements(SwappingHolder)
public class ShadowSwappingHolder {

    public void __constructor__(View itemView, MultiSelector multiSelector) {
        //do not delete, required by Robolectric
    }
}
