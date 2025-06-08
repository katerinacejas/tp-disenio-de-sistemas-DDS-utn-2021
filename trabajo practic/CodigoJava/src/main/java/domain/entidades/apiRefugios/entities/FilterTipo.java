package domain.entidades.apiRefugios.entities;

import domain.entidades.mascota.TipoDeMascota;

import java.util.ArrayList;
import java.util.List;

public class FilterTipo  implements Filter{
    TipoDeMascota tipo;

    public FilterTipo (TipoDeMascota tipo) {
        this.tipo = tipo;
    }

    public List<Hogar> filtrar(List<Hogar> hogares) {
        List<Hogar> hogaresFiltrados = new ArrayList<Hogar>();
        switch (tipo){
            case GATO:
                for (Hogar hogar : hogares) {
                    if (hogar.admisiones.gatos) {
                        hogaresFiltrados.add(hogar);
                    }
                }
                break;
            case PERRO:
                for (Hogar hogar : hogares) {
                    if (hogar.admisiones.perros) {
                        hogaresFiltrados.add(hogar);
                    }
                }
                break;
        }
        return hogaresFiltrados;
    }

}
