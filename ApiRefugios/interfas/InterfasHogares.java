package ApiRefugios.interfas;

import ApiRefugios.entities.ListaDeHogares;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface InterfasHogares {

    @GET("hogares")
    Call<ListaDeHogares> hogares(@Query("offset") int offset,  @Header("Authorization") String authHeader);


}
