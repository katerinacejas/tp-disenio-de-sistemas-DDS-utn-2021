package domain.entidades.apiRefugios.entities;

import com.google.gson.annotations.SerializedName;
import domain.entidades.mascota.CaracteristicaDeMascotas;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Hogar {
    @Id
    @GeneratedValue
    private int id_hogar;

    public int getId() {
        return id_hogar;
    }

    @Transient
    public String id;

    @Column(name = "nombre")
    public String nombre;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_ubicacion", referencedColumnName = "id_ubicacion")
    public Ubicacion ubicacion;

    @Column(name = "telefono")
    public String telefono;

    public void setAdmisiones(Admisiones admisiones) {
        this.admisiones = admisiones;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_admision", referencedColumnName = "id_admision")
    public Admisiones admisiones;

    @Column(name = "capacidad")
    public int capacidad;

    @Column(name = "lugares_disponibles")
    public int lugares_disponibles;

    @Column(name = "patio")
    public boolean patio;

    @ElementCollection
    public List<String> caracteristicas; //caracteristicas de mascota

    public List<String> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<String> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setLugares_disponibles(int lugares_disponibles) {
        this.lugares_disponibles = lugares_disponibles;
    }

    public void setPatio(boolean patio) {
        this.patio = patio;
    }

    public String getTelefono() {
        return telefono;
    }

    public Admisiones getAdmisiones() {
        return admisiones;
    }

    public boolean getPatio() {
        return patio;
    }

    @Entity
    @Table(name = "ubicacion")
    public static class Ubicacion {
        @Id
        @GeneratedValue
        private int id_ubicacion;

        public int getId() {
            return id_ubicacion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        @Column(name = "direccion")
        public String direccion;

        @Transient
        public double lat;
        @SerializedName(value = "long", alternate = "longi")
        @Transient
        public double longi;

        public String getDireccion() {
            return direccion;
        }

        public double getLat() {
            return lat;
        }

        public double getLongi() {
            return longi;
        }
    }

    @Entity
    @Table(name = "admision")
    public static class Admisiones {
        @Id
        @GeneratedValue
        private int id_admision;

        public int getId() {
            return id_admision;
        }

        @Column(name = "perros")
        public boolean perros;

        @Column(name = "gatos")
        public boolean gatos;

        public void setPerros(boolean perros) {
            this.perros = perros;
        }

        public void setGatos(boolean gatos) {
            this.gatos = gatos;
        }

        public boolean getPerros() {
            return perros;
        }

        public boolean getGatos() {
            return gatos;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getLugares_disponibles() {
        return lugares_disponibles;
    }
}
