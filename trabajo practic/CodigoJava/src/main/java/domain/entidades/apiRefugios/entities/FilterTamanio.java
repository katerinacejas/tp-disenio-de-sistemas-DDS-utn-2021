package domain.entidades.apiRefugios.entities;

import domain.entidades.mascota.TamanioMascota;

import java.util.ArrayList;
import java.util.List;

public class FilterTamanio implements Filter {
    TamanioMascota tamanio;

    public FilterTamanio (TamanioMascota tamanio) {
        this.tamanio = tamanio;
    }

    public List<Hogar> filtrar(List<Hogar> hogares) {
        if(tamanio == TamanioMascota.CHICA)
            return hogares;
        else{
            List<Hogar> hogaresFiltrados = new ArrayList<Hogar>();
            for (Hogar hogar : hogares) {
                if (hogar.patio) {
                    hogaresFiltrados.add(hogar);
                }
            }
            return hogaresFiltrados;
        }
    }
}
