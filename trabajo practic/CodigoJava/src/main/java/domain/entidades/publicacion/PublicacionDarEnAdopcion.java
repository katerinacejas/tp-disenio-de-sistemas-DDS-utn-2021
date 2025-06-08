package domain.entidades.publicacion;

import domain.entidades.mascota.Mascota;
import domain.entidades.organizacion.Organizacion;
import domain.entidades.usuario.Interesado;
import domain.entidades.usuario.Persona;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "publicacionDarEnAdopcion")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PublicacionDarEnAdopcion extends Publicacion {
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_mascota", referencedColumnName = "id_mascota")
    private Mascota mascota;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    private Persona persona;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<PreguntaDarEnAdopcion> preguntasDarEnAdopcion = new ArrayList<>();

    public static class PublicacionDarEnAdopcionDTO{
        public Mascota mascota;
        public Persona persona;
        public boolean aprobada;
        public Organizacion organizacion;

        public PublicacionDarEnAdopcionDTO(Mascota mascota, Persona persona, boolean aprobada, Organizacion organizacion) {
            this.mascota = mascota;
            this.persona = persona;
            this.aprobada = aprobada;
            this.organizacion = organizacion;
        }
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public Persona getPersona() {
        return persona;
    }

    public void agregarPregunta(PreguntaDarEnAdopcion preguntaDarEnAdopcion) {
        this.preguntasDarEnAdopcion.add(preguntaDarEnAdopcion);
    }
}
