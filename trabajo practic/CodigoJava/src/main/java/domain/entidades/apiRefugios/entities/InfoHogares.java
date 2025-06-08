package domain.entidades.apiRefugios.entities;

import javax.persistence.*;
import java.beans.ConstructorProperties;

@Entity
@Table(name = "infoHogares")
public class InfoHogares {
    @Id
    @GeneratedValue
    private int id_infoHogar;

    @Column(name = "ruta")
    private String ruta;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }


}
