package pl.dariuszbacinski.meteo.diagram;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;

public class BinderAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(PhotoView imageView, String url) {
        imageView.setScaleLevels(1f, 1.5f, 2f);
        Picasso.with(imageView.getContext()).load(url).config(Bitmap.Config.RGB_565).memoryPolicy(MemoryPolicy.NO_STORE).noFade().into(imageView);
    }
}

