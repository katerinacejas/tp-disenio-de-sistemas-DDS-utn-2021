package ApiRefugios.entities;

import Mascota.CaracteristicaDeMascotas;

import java.util.List;

public class Hogar {
    public String id;
    public String nombre;
    public Ubicacion ubicacion;
    public String telefono;
    public Admiciones admicion;
    public int capacidad;
    public int lugares_disponibles;
    public boolean patio;
    public List<Caracteristicas> caracteristica; //seria caracteristicas de mascota


    private class Ubicacion{ // puedo no crearla si no nos sirbe porque quizas esto ya lo haga la api
        public String direccion;
        public double lat;   //hay que escribirlos iguales = lat
        public double longi; //hay que escribirlos iguales = long
    }
    public class Admiciones{
        public boolean perros;
        public boolean gatos;

        public boolean isPerros() {
            return perros;
        }

    }
    private class Caracteristicas{
        public List<CaracteristicaDeMascotas> caracteristica; //no se

    }
    public int getCapacidad() {
        return capacidad;
    }

}
