package domain.entidades.publicacion;

import domain.entidades.organizacion.Organizacion;
import domain.entidades.usuario.Persona;

import javax.persistence.*;

@MappedSuperclass
public abstract class Publicacion {
    @Id
    @GeneratedValue
    private int id_publicacion;

    @Column(name = "aprobada")
    private Boolean aprobada;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_organizacion", referencedColumnName = "id_organizacion")
    private Organizacion organizacionAsignada;

    public void gestionarPublicacion(Boolean trueOrFalse) {
        aprobada = trueOrFalse;
    }

    public void setAprobada(Boolean aprobada) {
        this.aprobada = aprobada;
    }

    public void setOrganizacionAsignada(Organizacion organizacionAsignada) {
        this.organizacionAsignada = organizacionAsignada;
    }

    public Boolean isAprobada() {
        return aprobada;
    }

    public Organizacion getOrganizacionAsignada() {
        return organizacionAsignada;
    }

    public int getId() {
        return id_publicacion;
    }

}