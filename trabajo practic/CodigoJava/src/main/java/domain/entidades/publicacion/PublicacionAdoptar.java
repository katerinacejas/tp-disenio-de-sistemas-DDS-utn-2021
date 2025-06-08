package domain.entidades.publicacion;

import domain.controllers.InteresadoController;
import domain.entidades.mascota.TamanioMascota;
import domain.entidades.mascota.TipoDeMascota;
import domain.entidades.mascota.TipoSexo;
import domain.entidades.organizacion.Organizacion;
import domain.entidades.usuario.Interesado;
import domain.entidades.usuario.Persona;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "publicacionAdoptar")
public class PublicacionAdoptar extends Publicacion {
    @ManyToOne
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    private Persona persona;

    @Enumerated(EnumType.STRING)
    private TipoSexo sexo;

    @Enumerated(EnumType.STRING)
    private TamanioMascota tamanio;

    @Enumerated(EnumType.STRING)
    private TipoDeMascota tipo;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<PreguntaDarEnAdopcion> preguntasDarEnAdopcion = new ArrayList<>();





    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setSexo(TipoSexo sexo) {
        this.sexo = sexo;
    }

    public void setTamanio(TamanioMascota tamanio) {
        this.tamanio = tamanio;
    }

    public void setTipo(TipoDeMascota tipo) {
        this.tipo = tipo;
    }

    public Persona getPersona() {
        return persona;
    }

    public TipoSexo getSexo() {
        return sexo;
    }

    public TamanioMascota getTamanio() {
        return tamanio;
    }

    public TipoDeMascota getTipo() {
        return tipo;
    }

    public void agregarPregunta(PreguntaDarEnAdopcion preguntaDarEnAdopcion) {
        this.preguntasDarEnAdopcion.add(preguntaDarEnAdopcion);
    }
}
