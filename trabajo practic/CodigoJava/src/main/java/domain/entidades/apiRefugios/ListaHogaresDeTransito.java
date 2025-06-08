package domain.entidades.apiRefugios;

import domain.entidades.apiRefugios.entities.Hogar;
import domain.entidades.apiRefugios.entities.ListaDeHogares;
import domain.entidades.apiRefugios.interfas.ServicioHogares;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListaHogaresDeTransito {

    public List<Hogar> hogaresDisponibles = new ArrayList<>();


    public List<Hogar> actualizarListaHgares() throws IOException {
        ServicioHogares servicioHogares = ServicioHogares.getInstancia();
        ListaDeHogares listaDeHogares = servicioHogares.listaDeHogares(1);
        double CalcularPaginas = Math.ceil(listaDeHogares.getTotal()/10);
        int numeroDePaginas = (int)CalcularPaginas;

        if(hogaresDisponibles.size() == 0) {
            for (int id = 1; id <= numeroDePaginas; id = id + 1) {
                listaDeHogares = servicioHogares.listaDeHogares(id);

                for (Hogar unHogar : listaDeHogares.hogares) {
                    hogaresDisponibles.add(unHogar);
                }
            }
        }
        return hogaresDisponibles;
    }

}
