package domain.entidades.publicacion;

import domain.entidades.organizacion.Organizacion;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "preguntaObligartoria")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "preguntaObligartoria")
public class PreguntaObligatoria {
    @Id
    @GeneratedValue
    private int id_preguntaObligatoria;

    @Column(name = "pregunta")
    private String pregunta;

    @ElementCollection
    private List<String> respuesta;

    @Column(name = "esPreguntaDeRespuestaLibre")
    private Boolean esPreguntaDeRespuestaLibre;

    public int getId_preguntaObligatoria() {
        return id_preguntaObligatoria;
    }

    public void setId_preguntaObligatoria(int id_preguntaObligatoria) {
        this.id_preguntaObligatoria = id_preguntaObligatoria;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public List<String> getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(List<String> respuesta) {
        this.respuesta = respuesta;
    }

    public Boolean getEsPreguntaDeRespuestaLibre() {
        return esPreguntaDeRespuestaLibre;
    }

    public void setEsPreguntaDeRespuestaLibre(Boolean esPreguntaDeRespuestaLibre) {
        this.esPreguntaDeRespuestaLibre = esPreguntaDeRespuestaLibre;
    }
}