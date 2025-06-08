package domain.repositorios;

import domain.entidades.usuario.Interesado;

import java.util.ArrayList;
import java.util.List;

public class RepositorioInteresados extends Repositorio<Interesado> {

    private List<Interesado> interesados = new ArrayList<>();

    public void agregar(Interesado interesado) {
        interesados.add(interesado);
    }

    public List<Interesado> getInteresados() {
        return interesados;
    }
}