package pl.dariuszbacinski.meteo.location.model;

import retrofit.Response;
import retrofit.http.HEAD;
import retrofit.http.Query;
import rx.Observable;

public interface MeteoApiService2 {

    @HEAD("um/php/mgram_search.php")
    Observable<Response<Void>> getGridCoordinatedBasedOnLocation(@Query("NALL") double latitude, @Query("EALL") double longitude);
}
