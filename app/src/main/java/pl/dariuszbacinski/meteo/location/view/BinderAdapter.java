package pl.dariuszbacinski.meteo.location.view;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

public class BinderAdapter {

    @BindingAdapter("srcRes")
    public static void setSrc(ImageView view, int res){
        view.setImageResource(res);
    }
}
