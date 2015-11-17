package pl.dariuszbacinski.meteo.ui;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class PicassoBuilder {
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

    public PicassoBuilder withNetworkCache() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().header("Cache-Control", "max-age=" + (60 * 60)).build();

            }
        });

        okHttpClient.setCache(new Cache(context.getCacheDir(), Integer.MAX_VALUE));
        okHttpDownloader = new OkHttpDownloader(okHttpClient);
        return this;
    }
}
