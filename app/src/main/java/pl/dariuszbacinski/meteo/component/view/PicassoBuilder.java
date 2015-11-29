package pl.dariuszbacinski.meteo.component.view;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class PicassoBuilder {
    public static final int ONE_HOUR = 60 * 60;
    private Context context;
    private OkHttpDownloader okHttpDownloader;

    public PicassoBuilder(Context context) {
        this.context = context.getApplicationContext();
    }

    public Picasso build() {
        if (okHttpDownloader != null) {
            return new Picasso.Builder(context).downloader(okHttpDownloader).build();
        } else {
            return Picasso.with(context);
        }
    }

    public PicassoBuilder withNetworkCache(Cache cache) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().header("Cache-Control", "max-age=" + ONE_HOUR).build();
            }
        });

        okHttpClient.setCache(cache);
        okHttpDownloader = new OkHttpDownloader(okHttpClient);
        return this;
    }
}
