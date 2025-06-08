package domain.entidades.usuario;

import domain.entidades.contrasenia.Contrasenia;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class DatosBasicos {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    public int getId() {
        return id;
    }

    @Column(name = "nombre")
    protected String nombre;

    @Column(name = "apellido")
    protected String apellido;

    @Column(name = "domicilio")
    protected String domicilio;

    @Column(name = "fechaDeNacimiento", columnDefinition = "DATE")
    protected Date fechaDeNacimiento;

    @Enumerated(EnumType.STRING)
    protected TipoDeDocumento tipoDeDocumento;

    @Column(name = "documento")
    protected String documento;

    @Column(name = "nTramite")
    protected String nTramite;

    public String getNombre(){
        return nombre;
    }

    public String getApellido(){
        return apellido;
    }

    public String getDomicilio(){
        return domicilio;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public TipoDeDocumento getTipoDeDocumento() {
        return this.tipoDeDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public void setTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
        this.tipoDeDocumento = tipoDeDocumento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getnTramite() {
        return nTramite;
    }

    public void setnTramite(String nTramite) {
        this.nTramite = nTramite;
    }

    public boolean validarContrasenia(String contrasenia) {
        Contrasenia validador = new Contrasenia();
        return validador.contraseniaValida(contrasenia);
    }

}