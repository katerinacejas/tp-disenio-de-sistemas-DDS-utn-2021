package ApiRefugios;

import ApiRefugios.entities.Hogar;
import ApiRefugios.entities.ListaDeHogares;
import ApiRefugios.interfas.ServicioHogares;

import java.io.IOException;

public class EjemploDeUso {

    public static void main(String[] args) throws IOException {
        ServicioHogares sevicioHogares = ServicioHogares.getInstancia();


        for(int id = 1; id < 5; id = id + 1){
            ListaDeHogares listaDeHogares = sevicioHogares.listaDeHogares(id);

            for(Hogar unHogar: listaDeHogares.hogares){
                if(unHogar.getCapacidad()>150){
                    System.out.println(unHogar.id + " " + unHogar.nombre );
                 }


            }
        }

    }

}
