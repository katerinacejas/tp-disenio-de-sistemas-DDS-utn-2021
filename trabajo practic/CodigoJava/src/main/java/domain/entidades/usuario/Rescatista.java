package domain.entidades.usuario;

import domain.entidades.apiRefugios.entities.Filter;
import domain.entidades.mascota.TipoDeMascota;
import domain.entidades.publicacion.PublicacionMascotaPerdida;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "rescatista")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Rescatista extends DatosBasicos {

    @ElementCollection
    private List<String> rutaFotos; //dirección de las fotos de la mascota cuando la encontró.

    @Column(name = "descripcionMascotra")
    private String descripcionMascota;

    @Column(name = "lugardondeEncontroAMascota")
    private String lugarDondeEncontroAMascota;

    @Column(name = "latitud")
    private String latitud;

    @Column(name = "longitud")
    private String longitud;

    @Enumerated(EnumType.STRING)
    private TipoDeMascota tipoDeMascota;

    @OneToMany(mappedBy = "formulario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PublicacionMascotaPerdida> publicaciones;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Contacto contacto;

    public Rescatista() {
        this.rutaFotos = new ArrayList<>();
    }


    public void setDescripcionMascota(String descripcionMascota) {
        this.descripcionMascota = descripcionMascota;
    }

    public void setLugarDondeEncontroAMascota(String lugarDondeEncontroAMascota) {
        this.lugarDondeEncontroAMascota = lugarDondeEncontroAMascota;
    }

    public String getDescripcionMascota() {
        return descripcionMascota;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public void setRutaFotos(List<String> rutaFotos) {
        this.rutaFotos = rutaFotos;
    }

    public TipoDeMascota getTipoDeMascota() {
        return tipoDeMascota;
    }

    public void setTipoDeMascota(TipoDeMascota tipoDeMascota) {
        this.tipoDeMascota = tipoDeMascota;
    }

    public String getLugarDondeEncontroAMascota() {
        return lugarDondeEncontroAMascota;
    }

    public void setPublicacion(List<PublicacionMascotaPerdida> publicaciones) {
        for (PublicacionMascotaPerdida publicacion : publicaciones) {
            this.publicaciones.add(publicacion);
        }
    }

    public String getLatitud() {
        return latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    public void agregarFotos(List<String> unaRuta) {
        this.rutaFotos.addAll(unaRuta);
    }


    public Contacto getContacto() {
        return contacto;
    }
}



