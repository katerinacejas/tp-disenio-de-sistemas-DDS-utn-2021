package domain.entidades.publicacion;

import domain.entidades.organizacion.Organizacion;

import javax.persistence.*;

@Entity
@DiscriminatorValue("noObligatoria")
public class PreguntaDarEnAdopcion extends PreguntaObligatoria {

    @ManyToOne
    @JoinColumn(name = "id_organizacion", referencedColumnName = "id_organizacion")
    private Organizacion organizacion;

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

}
