package domain.entidades.usuario;

import com.textmagic.sdk.RestException;
import domain.entidades.usuario.formasNotificacion.EstrategiaDeNotificacion;

import javax.mail.MessagingException;
import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "contacto")
public class Contacto {
    @Id
    @GeneratedValue
    private int id_contacto;

    public int getId() {
        return id_contacto;
    }

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "mail")
    private String mail;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EstrategiaDeNotificacion> formasDeNotificacion = new ArrayList<>();

    /*public Contacto() {
    this.formasDeNotificacion = new ArrayList<>();
    }*/



    public void agregarNotificacion(EstrategiaDeNotificacion notificacion) {
        formasDeNotificacion.add(notificacion);
    }

    public void notificar(String mensaje) {
        this.formasDeNotificacion.forEach(v -> {
            try {
                v.notificar(this, mensaje);
            } catch (RestException | IOException | MessagingException e) {
                e.printStackTrace();
            }
        });
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String devolverMail() {
        if(this.getMail() == null){return " ";}
        else {
            return this.getMail();
        }
    }
    public String devolverTelefono() {
        if(this.getTelefono() == null){return " ";}
        else {
            return this.getTelefono();
        }

    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }



    public void setFormasDeNotificacion(List<EstrategiaDeNotificacion> formasDeNotificacion) {
        this.formasDeNotificacion = formasDeNotificacion;
    }


    public void agregarFormaDeNotificacion(EstrategiaDeNotificacion estrategia) {
        this.formasDeNotificacion.add(estrategia);
    }

    public List<EstrategiaDeNotificacion> getFormasDeNotificacion() {
        return formasDeNotificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getMail() {
        return mail;
    }


}

