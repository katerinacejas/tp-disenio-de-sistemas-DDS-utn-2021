package domain.repositorios;

import domain.entidades.publicacion.PreguntaObligatoria;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPreguntas extends Repositorio<PreguntaObligatoria> {
    private List<PreguntaObligatoria> preguntas = new ArrayList<>();

    public void agregar(PreguntaObligatoria pregunta) {
        preguntas.add(pregunta);
    }

    public List<PreguntaObligatoria> getPreguntas() {
        return preguntas;
    }
}
