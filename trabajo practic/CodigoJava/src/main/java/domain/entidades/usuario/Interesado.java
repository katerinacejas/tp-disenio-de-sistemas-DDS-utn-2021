package domain.entidades.usuario;

import domain.entidades.mascota.TamanioMascota;
import domain.entidades.mascota.TipoDeMascota;
import domain.entidades.mascota.TipoSexo;
import domain.entidades.publicacion.PublicacionAdoptar;
import domain.entidades.publicacion.PublicacionDarEnAdopcion;

import javax.persistence.*;

@Entity
@Table(name = "interesado")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Interesado extends Usuario {

   /*
       @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    public int getId() {
        return id;
    }

   @ManyToOne(cascade = {CascadeType.ALL})      // TODO no es Many to One (un usuario puede llegar a tener muchas publicaciones de interes?)?
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    private Usuario usuario;

    */

    @Enumerated(EnumType.STRING)
    private TipoSexo sexoMascotaInteresada;

    @Enumerated(EnumType.STRING)
    private TamanioMascota tamanioMascotaInteresada;

    @Enumerated(EnumType.STRING)
    private TipoDeMascota tipoMascotaInteresada;

    @Column(name = "esPerro")
    private Boolean esPerro;

    @Column(name = "tienePatio")
    private Boolean tienePatio;

    @Column(name = "tieneOtrasMascotas")
    private Boolean tieneOtrasMascotas;

    @Column(name = "tieneTiempo")
    private Boolean tieneTiempo;


    public boolean leSirve(PublicacionDarEnAdopcion publicacion) {
        return sexoMascotaInteresada == publicacion.getMascota().getSexo() && tamanioMascotaInteresada == publicacion.getMascota().getTamanio() && tipoMascotaInteresada == publicacion.getMascota().getTipo();
    }

    public TipoSexo getSexoMascotaInteresada() {
        return sexoMascotaInteresada;
    }

    public TamanioMascota getTamanioMascotaInteresada() {
        return tamanioMascotaInteresada;
    }

    public TipoDeMascota getTipoMascotaInteresada() {
        return tipoMascotaInteresada;
    }

    public void enviarPublicacion() {
        getListaContacto().forEach(c -> c.notificar("Puede haber mascotas en adopcion que te interesen"));
    }

    public Boolean getTienePatio() {
        return tienePatio;
    }

    public void setTienePatio(Boolean tienePatio) {
        this.tienePatio = tienePatio;
    }

    public Boolean getTieneOtrasMascotas() {
        return tieneOtrasMascotas;
    }

    public void setTieneOtrasMascotas(Boolean tieneOtrasMascotas) {
        this.tieneOtrasMascotas = tieneOtrasMascotas;
    }

    public Boolean getEsPerro() {
        return esPerro;
    }

    public void setEsPerro(Boolean esPerro) {
        this.esPerro = esPerro;
    }

    public Boolean getTieneTiempo() {
        return tieneTiempo;
    }

    public void setTieneTiempo(Boolean tieneTiempo) {
        this.tieneTiempo = tieneTiempo;
    }

    public void setSexo(TipoSexo sexo) {
        this.sexoMascotaInteresada = sexo;
    }

    public void setTamanio(TamanioMascota tamanio) {
        this.tamanioMascotaInteresada = tamanio;
    }

    public void setTipo(TipoDeMascota tipo) {
        this.tipoMascotaInteresada = tipo;
    }

}