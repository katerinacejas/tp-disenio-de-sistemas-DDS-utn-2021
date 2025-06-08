package domain.entidades.publicacion;

import domain.entidades.mascota.Mascota;
import domain.entidades.organizacion.Organizacion;
import domain.entidades.usuario.Rescatista;

import javax.persistence.*;

@Entity
@Table(name = "publicacionMascotaPerdida")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PublicacionMascotaPerdida extends Publicacion {

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_formulario", referencedColumnName = "id")
    private Rescatista formulario;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "rutaFotos")
    private String pathFotosEnCarrusel;

    @Column(name = "encontradaPorRescatista")
    private Boolean encontradaPorRescatista;

    @Column(name = "duenioLaBusca")
    private Boolean duenioLaBusca;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_mascota", referencedColumnName = "id_mascota")
    private Mascota mascota;

    public PublicacionMascotaPerdida(){}

    @Override
    public void setAprobada(Boolean aprobada) {
        super.setAprobada(aprobada);
    }

    public void setFormulario(Rescatista formulario) {
        this.formulario = formulario;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPathFotosEnCarrusel(String pathFotosEnCarrusel) {
        this.pathFotosEnCarrusel = pathFotosEnCarrusel;
    }

    public Rescatista getFormulario() {
        return formulario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPathFotosEnCarrusel() {
        return pathFotosEnCarrusel;
    }

    public static class PublicacionMascotaPerdidaDTO{
        public Organizacion organizacion;
       public boolean aprobada = false;
        public Rescatista formulario;
        public String descripcion;
        public String pathFotosEnCarrusel;

        public PublicacionMascotaPerdidaDTO(Organizacion organizacion,Rescatista formulario, String descripcion, String pathFotosEnCarrusel) {
            this.organizacion = organizacion;
            this.formulario = formulario;
            this.descripcion = descripcion;
            this.pathFotosEnCarrusel = pathFotosEnCarrusel;

        }
    }

    public Boolean getEncontradaPorRescatista() {
        return encontradaPorRescatista;
    }

    public void setEncontradaPorRescatista(Boolean encontradaPorRescatista) {
        this.encontradaPorRescatista = encontradaPorRescatista;
    }

    public Boolean getDuenioLaBusca() {
        return duenioLaBusca;
    }

    public void setDuenioLaBusca(Boolean duenioLaBusca) {
        this.duenioLaBusca = duenioLaBusca;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public void contactarRescatista() {
        formulario.getContacto().notificar("mensaje"); // TODO definir mensaje
    }
}
