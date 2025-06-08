package domain.entidades.mascota;

import domain.entidades.usuario.Persona;
import domain.entidades.usuario.Usuario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mascota")
public class Mascota {
    @Id
    @GeneratedValue
    private int id_mascota;

    public int getId() {
        return id_mascota;
    }

    @Column(name = "nombre")
    private String nombreMascota;

    @Column(name = "apodo")
    private String apodo;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "rutaFoto")
    private String ruta; // Dirección en donde está guardada la UNICA foto de la mascota

    @Column(name = "perdida")
    private Boolean perdida;

    @Column(name = "quererDarlaEnAdopcion")
    private Boolean quererDarlaEnAdopcion;

    @Enumerated(EnumType.STRING)
    private TipoSexo sexo;

    @Enumerated(EnumType.STRING)
    private TamanioMascota tamanio;

    @OneToMany
    @JoinColumn(name = "id_mascota", referencedColumnName = "id_mascota")
    private List<CaracteristicaDeMascotas> caracteristicas;

    @Enumerated(EnumType.STRING)
    private TipoDeMascota tipo;

    @Column(name = "descripcionFisica")
    private String descripcionFisica;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    private Persona duenio;


    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Usuario", referencedColumnName = "id")
    private Usuario usuario;

    public Mascota(){

    }



    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Boolean getPerdida() {
        return perdida;
    }

    public void setPerdida(Boolean perdida) {
        this.perdida = perdida;
    }

    public TipoSexo getSexo() {
        return sexo;
    }

    public void setSexo(TipoSexo sexo) {
        this.sexo = sexo;
    }

    public TamanioMascota getTamanio() {
        return tamanio;
    }

    public void setTamanio(TamanioMascota tamanio) {
        this.tamanio = tamanio;
    }

    public List<CaracteristicaDeMascotas> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<CaracteristicaDeMascotas> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public void setQuererDarlaEnAdopcion(Boolean quererDarlaEnAdopcion) {
        this.quererDarlaEnAdopcion = quererDarlaEnAdopcion;
    }

    public TipoDeMascota getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeMascota tipo) {
        this.tipo = tipo;
    }

    public String getDescripcionFisica() {
        return descripcionFisica;
    }

    public void setDescripcionFisica(String descripcionFisica) {
        this.descripcionFisica = descripcionFisica;
    }

   // public int getId() {
    //    return id;
  //  }

   // public void setId(int id) {
  //      this.id = id;

    public Boolean getQuererDarlaEnAdopcion() {
        return quererDarlaEnAdopcion;
    }

    //ecuidado dueño
    public Persona getDuenio1() {
        return duenio;
    }
    //cuiado duenio
    public Usuario getDuenio() {
        return usuario;
    }

    public void setDuenio(Persona unDuenio) {
        this.duenio = unDuenio;
    }

    public void setDuenio(Usuario unUsuario) {
        this.usuario = unUsuario;
    }

    public TamanioMascota obtenerTamanio(String tamanio) {
        switch (tamanio) {
            case "Grande":
                return TamanioMascota.GRANDE;
            case "Mediana":
                return TamanioMascota.MEDIANA;
            case "Chica":
                return TamanioMascota.CHICA;
        }
        return null;
    }

    public TipoDeMascota obtenerTipo(String tipo) {
        if(tipo.equals("Gato")) {
            return TipoDeMascota.GATO;
        } else if(tipo.equals("Perro")) {
            return TipoDeMascota.PERRO;
        }
        return null;
    }

    public static class MascotaDTO {
        public String nombre;
        public String apodo;
        public Integer edad;
        public Boolean perdida;
        public String ruta;
        public TipoSexo sexo;
        public TamanioMascota tamanio;
        public List<CaracteristicaDeMascotas> caracteristicas;
        public TipoDeMascota tipo;
        public String descripcionFisica;
        public Persona duenio;

        public MascotaDTO(String nombre, String apodo, int edad, Boolean perdida, TipoSexo sexo, TamanioMascota tamanio, TipoDeMascota tipo, CaracteristicaDeMascotas caracteristica,  Persona duenio) {
        this.nombre = nombre;
        this.apodo = apodo;
        this.edad = edad;
        this.perdida = perdida;
        this.sexo = sexo;
        this.tipo = tipo;
        this.caracteristicas = new ArrayList<>();
        this.caracteristicas.add(caracteristica);
        this.duenio = duenio;

        }
    }
    }

