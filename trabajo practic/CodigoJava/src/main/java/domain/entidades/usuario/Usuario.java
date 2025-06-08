package domain.entidades.usuario;

import domain.entidades.contrasenia.Contrasenia;
import domain.entidades.mascota.Mascota;
import domain.entidades.mascota.TipoDeMascota;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuario")
//@Inheritance(strategy = InheritanceType.JOINED)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Usuario extends DatosBasicos {
    @Column(name = "usuario")
    private String usuario;

    @Column(name = "contrasenia")
    private String contrasenia;

    @Column(name = "email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contacto> listaContacto = new ArrayList<>();

    @OneToMany(mappedBy = "duenio", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Mascota> listaMascotas;

    public Usuario() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        Contrasenia contraseniaHasheada = new Contrasenia();
        String contrHash = contraseniaHasheada.contraseniaHash(contrasenia);
        this.contrasenia = contrHash;
    }

    public void setListaContacto(List<Contacto> listaContacto) {
        this.listaContacto = listaContacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public TipoDeDocumento getTipoDeDocumento() {
        return tipoDeDocumento;
    }

    public void setTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
        this.tipoDeDocumento = tipoDeDocumento;
    }

    public void agregarContacto(Contacto contacto){
        this.listaContacto.add(contacto);
    }


    public List<Contacto> getListaContacto() {
        return listaContacto;
    }
    public List<Mascota> getListaMascotas() { return listaMascotas; }
    public void agregarMascota(Mascota mascota){
        listaMascotas.add(mascota);
    }

    public Integer pasarDocumento(String documento){
        int documentoInt = Integer.parseInt(documento);
        return documentoInt;
    }
}