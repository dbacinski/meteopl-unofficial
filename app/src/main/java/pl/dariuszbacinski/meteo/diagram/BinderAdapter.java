package pl.dariuszbacinski.meteo.diagram;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import pl.dariuszbacinski.meteo.R;
import uk.co.senab.photoview.PhotoView;

public class BinderAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(PhotoView imageView, String url) {
        float minScale = imageView.getContext().getResources().getInteger(R.integer.image_scale_min);
        float maxScale = imageView.getContext().getResources().getInteger(R.integer.image_scale_max);
        float mediumScale = (maxScale + minScale) / 2f;
        imageView.setScaleLevels(minScale, mediumScale, maxScale);
        Picasso.with(imageView.getContext()).load(url).config(Bitmap.Config.RGB_565).memoryPolicy(MemoryPolicy.NO_STORE).noFade().into(imageView);
    }
}

