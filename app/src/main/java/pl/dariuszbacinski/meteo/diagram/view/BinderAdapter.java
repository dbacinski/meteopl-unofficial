package pl.dariuszbacinski.meteo.diagram.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;

import com.squareup.okhttp.Cache;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import hugo.weaving.DebugLog;
import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.component.view.PicassoBuilder;
import uk.co.senab.photoview.PhotoView;

public class BinderAdapter {

    @DebugLog
    @BindingAdapter("bind:imageUrl")
    public static void loadImage(PhotoView imageView, String url) {
        float minScale = imageView.getContext().getResources().getInteger(R.integer.image_scale_min);
        float maxScale = imageView.getContext().getResources().getInteger(R.integer.image_scale_max);
        float mediumScale = (maxScale + minScale) / 2f;
        imageView.setScaleLevels(minScale, mediumScale, maxScale);
        PicassoHolder.getInstance(imageView.getContext()).load(url).config(Bitmap.Config.RGB_565).memoryPolicy(MemoryPolicy.NO_STORE).noFade().into(imageView, new OnLoadedScaleUpdater(imageView, minScale));
    }

    private static class PicassoHolder {
        private static Picasso singleton;

        public static Picasso getInstance(Context context) {
            if (singleton == null) {
                synchronized (Picasso.class) {
                    if (singleton == null) {
                        setInstance(new PicassoBuilder(context).withNetworkCache(new Cache(context.getCacheDir(), Integer.MAX_VALUE)).build());
                    }
                }
            }
            return singleton;
        }

        public static void setInstance(Picasso singleton) {
            PicassoHolder.singleton = singleton;
        }
    }

    private static class OnLoadedScaleUpdater implements Callback {
        private PhotoView imageView;
        private float minScale;

        public OnLoadedScaleUpdater(PhotoView imageView, float minScale) {
            this.imageView = imageView;
            this.minScale = minScale;
        }

        @Override
        public void onSuccess() {
            //XXX holds reference to image view during network call
            imageView.setScale(minScale, 0, 0, false);
        }

        @Override
        public void onError() {

        }
    }
}

