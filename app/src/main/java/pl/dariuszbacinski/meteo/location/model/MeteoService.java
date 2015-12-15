package pl.dariuszbacinski.meteo.location.model;

import android.net.Uri;

import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;

public class MeteoService {

    MeteoApiService2 service;

    public MeteoService() {
        //TODO move Retrofit to parameter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.meteo.pl")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.service = retrofit.create(MeteoApiService2.class);
    }

    public Observable<Location> getGridCoordinatedBasedOnLocation(android.location.Location location) {

        return service.getGridCoordinatedBasedOnLocation(location.getLatitude(), location.getLongitude()).map(new Func1<Response<Void>, Location>() {
            @Override
            public Location call(Response<Void> response) {
               //TODO extract coordinates from response
                Uri uri = Uri.parse(response.raw().request().url().toString());
                return new Location("", Integer.valueOf(uri.getQueryParameter("row")), Integer.valueOf(uri.getQueryParameter("col")));
            }
        });
    }
}
