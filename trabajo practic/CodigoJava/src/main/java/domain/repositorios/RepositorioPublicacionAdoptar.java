package domain.repositorios;

import domain.entidades.publicacion.PublicacionAdoptar;

import java.util.ArrayList;
import java.util.List;

public class RepositorioPublicacionAdoptar extends Repositorio<RepositorioPublicacionAdoptar> {

    private List<PublicacionAdoptar> publicacionesAdoptar = new ArrayList<>();

    public void agregar(PublicacionAdoptar publicacionAdoptar) {
        publicacionesAdoptar.add(publicacionAdoptar);
    }

    public List<PublicacionAdoptar> getPublicacionesAdoptar() {
        return publicacionesAdoptar;
    }
}
