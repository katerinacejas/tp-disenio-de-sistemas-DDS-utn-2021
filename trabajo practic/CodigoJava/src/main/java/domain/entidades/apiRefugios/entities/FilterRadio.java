package domain.entidades.apiRefugios.entities;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class FilterRadio implements Filter{
    int radio;
    int longi;
    int lat;

    public FilterRadio (int radio, int longi, int lat) {
        this.lat = lat;
        this.radio = radio;
        this.longi = longi;
    }

    public List<Hogar> filtrar(List<Hogar> hogares){
        List<Hogar> hogaresFiltrados = new ArrayList<Hogar>();
        for (Hogar hogar : hogares) {
            if (distanciaPuntos(longi, lat, hogar.ubicacion.longi, hogar.ubicacion.lat) < radio) {
                hogaresFiltrados.add(hogar);
            }
        }
        return hogaresFiltrados;
    }

    public Double distanciaPuntos(Integer x1, Integer y1, Double x2, Double y2){
        return sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
}
