package domain.entidades.apiRefugios.interfas;

import domain.entidades.apiRefugios.entities.ListaDeHogares;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioHogares {
    private static ServicioHogares instancia = null;
    private static final String urlAPI = "https://api.refugiosdds.com.ar/api/";
    private Retrofit retrofit;

    private ServicioHogares(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioHogares getInstancia(){
        if(instancia == null){
            instancia = new ServicioHogares();
        }
        return instancia;
    }


    public ListaDeHogares listaDeHogares(int id) throws IOException {
        InterfasHogares interfasHogares = this.retrofit.create(InterfasHogares.class);
        Call<ListaDeHogares> requestListadoDeHogares = interfasHogares.hogares(id,"Bearer CSN4rGPogpUjadBjSbEuwsgZdf02hSX1UtBfGJhsJZEUSO8T21322Oue70V4");
        Response<ListaDeHogares> responseListaDeHogares= requestListadoDeHogares.execute(); //puede tirar expcecion porque aca llama a la api y machea con mi molde
        return responseListaDeHogares.body();
    }

}
