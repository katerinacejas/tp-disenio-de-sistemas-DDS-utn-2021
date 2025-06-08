package domain.entidades.usuario;

import domain.entidades.mascota.Mascota;
import domain.entidades.usuario.formasNotificacion.EstrategiaDeNotificacion;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Persona extends DatosBasicos {

    public void setListaContacto(List<Contacto> listaContacto) {
        this.listaContacto = listaContacto;
    }

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Contacto> listaContacto;

    public void setListaMascotas(List<Mascota> listaMascotas) {
        this.listaMascotas = listaMascotas;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Mascota> listaMascotas;

    public Persona() {
        this.listaMascotas = new ArrayList<>();
        this.listaContacto = new ArrayList<>();
    }



    public List<Contacto> getListaContacto() {
        return listaContacto;
    }

    public List<Mascota> getListaMascotas() {
        return listaMascotas;
    }


    public void agregarMascota(Mascota mascota) {
        listaMascotas.add(mascota);
    }

}

