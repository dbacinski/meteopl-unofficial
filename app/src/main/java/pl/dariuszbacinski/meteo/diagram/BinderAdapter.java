package pl.dariuszbacinski.meteo.diagram;

import android.databinding.BindingAdapter;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;

public class BinderAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(PhotoView imageView, String url) {
        imageView.setScaleLevels(1f, 1.5f, 2f);
        Picasso.with(imageView.getContext()).load(url).memoryPolicy(MemoryPolicy.NO_STORE).fit().centerInside().noFade().into(imageView);
    }
}

