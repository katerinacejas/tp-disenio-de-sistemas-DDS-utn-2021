package domain.entidades.usuario;

import domain.entidades.contrasenia.Contrasenia;
import domain.entidades.organizacion.Organizacion;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "voluntario")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Voluntario extends DatosBasicos {
    @ManyToOne
    @JoinColumn(name = "id_organizacion", referencedColumnName = "id_organizacion")
    private Organizacion organizacion; /*= new Organizacion();*/

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "contrasenia")
    private String contrasenia;

    @Column(name = "email")
    private String email;

    public Voluntario() {

    }




    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasenia(String contrasenia) {
        Contrasenia contraseniaHasheada = new Contrasenia();
        String contrHash = contraseniaHasheada.contraseniaHash(contrasenia);
        this.contrasenia = contrHash;
    }

    public boolean validarContrasenia(String contrasenia) {
        Contrasenia validador = new Contrasenia();
        return validador.contraseniaValida(contrasenia);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getEmail() {
        return email;
    }
}

