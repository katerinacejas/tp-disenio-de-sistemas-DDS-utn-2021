package domain.entidades.organizacion;

import domain.entidades.mascota.CaracteristicaDeMascotas;
import domain.entidades.mascota.Mascota;
import domain.entidades.publicacion.PreguntaDarEnAdopcion;
import domain.entidades.publicacion.PreguntaObligatoria;
import domain.entidades.usuario.Voluntario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="organizacion")
public class Organizacion {
    @Id
    @GeneratedValue
    private int id_organizacion;

    @Column(name="domicilio")
    private String domicilio;

    @OneToMany
    @JoinColumn(name = "id_organizacion", referencedColumnName = "id_organizacion")
    private List<Mascota> mascotasEnAdopcion;

    @OneToMany
    @JoinColumn(name = "id_organizacion", referencedColumnName = "id_organizacion")
    private List<Mascota> mascotasRescatadas;

    @Column(name="redesSociales")
    private String redesSociales;

    @OneToMany(mappedBy = "organizacion", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<PreguntaDarEnAdopcion> preguntasDarAdopcion = new ArrayList<>();

    @OneToMany(mappedBy = "organizacion", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Voluntario> voluntarios = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "id_organizacion", referencedColumnName = "id_organizacion")
    private List<CaracteristicaDeMascotas> caracteristicas;  //CaracteristicaDeMascotas es una clase que tiene solo string caracteristica

    @Column(name="nombre")
    private String nombre;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="rutaFoto")
    private String rutaFoto;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public Organizacion(){

    }
    public int getId() {
        return id_organizacion;
    }
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void setMascotasEnAdopcion(List<Mascota> mascotasEnAdopcion) {
        this.mascotasEnAdopcion = mascotasEnAdopcion;
    }

    public void setMascotasRescatadas(List<Mascota> mascotasRescatadas) {
        this.mascotasRescatadas = mascotasRescatadas;
    }
    public List<PreguntaDarEnAdopcion> getPreguntasDarAdopcion() {
        return preguntasDarAdopcion;
    }

    public void setPreguntasDarAdopcion(List<PreguntaDarEnAdopcion> preguntasDarAdopcion) {
        this.preguntasDarAdopcion = preguntasDarAdopcion;
    }


    public void setCaracteristicas(CaracteristicaDeMascotas caracteristicas) {
        this.caracteristicas.add(caracteristicas);
    }

    public List<CaracteristicaDeMascotas> getCaracteristicas() {
        return caracteristicas;
    }

    public void agregarVoluntario(Voluntario voluntarioNuevo) {
        voluntarios.add(voluntarioNuevo);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public static class OrganizacionDTO {
        public String nombre;
        public String domicilio;
        public List<PreguntaObligatoria> preguntasDarAdopcion;
        public List<Voluntario> voluntarios;
        public List<CaracteristicaDeMascotas> caracteristicas;

        public OrganizacionDTO(String nombre, String unDomicilio, List<Voluntario> unosVoluntarios
                , List<PreguntaObligatoria> preguntasDarAdopcion, List<CaracteristicaDeMascotas> unasCaracteristicas){
            this.nombre = nombre;
            this.domicilio = unDomicilio;
            this.voluntarios = unosVoluntarios;
            this.caracteristicas = unasCaracteristicas;
            this.preguntasDarAdopcion = preguntasDarAdopcion;
        }
    }

    public String getDomicilio() {
        return domicilio;
    }

    public List<Voluntario> getVoluntarios() {
        return voluntarios;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId_organizacion() {
        return id_organizacion;
    }
}