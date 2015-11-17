package pl.dariuszbacinski.meteo.diagram.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.ui.PicassoBuilder;
import uk.co.senab.photoview.PhotoView;

public class BinderAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(PhotoView imageView, String url) {
        //TODO add http cache
        float minScale = imageView.getContext().getResources().getInteger(R.integer.image_scale_min);
        float maxScale = imageView.getContext().getResources().getInteger(R.integer.image_scale_max);
        float mediumScale = (maxScale + minScale) / 2f;
        imageView.setScaleLevels(minScale, mediumScale, maxScale);
        PicassoHolder.getInstance(imageView.getContext()).load(url).config(Bitmap.Config.RGB_565).memoryPolicy(MemoryPolicy.NO_STORE).noFade().into(imageView);
    }

    private static class PicassoHolder {
        public static Picasso singleton;

        public static Picasso getInstance(Context context) {
            if (singleton == null) {
                synchronized (Picasso.class) {
                    if (singleton == null) {
                        singleton = new PicassoBuilder(context).withNetworkCache().build();
                    }
                }
            }
            return singleton;
        }
    }
}

