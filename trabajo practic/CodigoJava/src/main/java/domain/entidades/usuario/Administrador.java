package domain.entidades.usuario;

import domain.entidades.contrasenia.Contrasenia;
import domain.entidades.organizacion.Organizacion;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "administrador")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Administrador extends DatosBasicos {

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_organizacion")
    private Organizacion organizacion;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "contrasenia")
    private String contrasenia;

    @Column(name = "email")
    private String email;


    public Administrador() {

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

    public String getUsuario() {
        return usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Organizacion getOrganizacion() {
        return organizacion;
    }
    /*public Administrador(Organizacion unaOrganizacion, String unUsuario, String unaContrasenia, String unEmail, String unNombre, String unApellido, String unDomicilio, LocalDate unaFechaDeNac,
                         TipoDeDocumento unTipoDeDNI, Integer unDNI){
        this.organizacion = unaOrganizacion;
        this.usuario = unUsuario;
        this.contrasenia = unaContrasenia;
        this.email = unEmail;
        this.setNombre(unNombre);
        this.setApellido(unApellido);
        this.setDomicilio(unDomicilio);
        this.setFechaDeNacimiento(unaFechaDeNac);
        this.setTipoDeDocumento(unTipoDeDNI);
        this.setDocumento(unDNI);
    } */





    /*public void aceptarAdministrador(Organizacion unaOrganizacion, String unUsuario, String unaContrasenia, String unEmail,
                                     String unNombre, String unApellido, String unDomicilio, LocalDate unaFechaDeNac,
                                     TipoDeDocumento unTipoDeDNI, Integer unDNI){
        Administrador administrador = new Administrador(unaOrganizacion, unUsuario, unaContrasenia, unEmail);
        administrador.setNombre(unNombre);
        administrador.setApellido(unApellido);
        administrador.setDomicilio(unDomicilio);
        administrador.setFechaDeNacimiento(unaFechaDeNac);
        administrador.setTipoDeDocumento(unTipoDeDNI);
        administrador.setDocumento(unDNI);
    }*/

    /*public void agregarCaracteristica(String caracteristica){
      organizacion.agregarCaracteristica(caracteristica);
    }
 */

}