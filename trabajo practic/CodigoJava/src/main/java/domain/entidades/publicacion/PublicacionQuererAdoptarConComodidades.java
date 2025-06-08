package domain.entidades.publicacion;

import domain.entidades.usuario.Interesado;

import javax.persistence.*;

@Entity
@Table(name = "publicacionQuererAdoptarConComodidades")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PublicacionQuererAdoptarConComodidades extends Publicacion{
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_formulario", referencedColumnName = "id")
    private Interesado formulario;


    public PublicacionQuererAdoptarConComodidades(){}

    @Override
    public void setAprobada(Boolean aprobada) {
        super.setAprobada(aprobada);
    }

    public Interesado getFormulario() {
        return formulario;
    }

    public void setFormulario(Interesado formulario) {
        this.formulario = formulario;
    }
}
