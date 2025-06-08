package domain.repositorios;

import domain.entidades.publicacion.PublicacionQuererAdoptarConComodidades;

import java.util.ArrayList;
import java.util.List;

public class RepositorioPublicacionIntencionAdoptar {

    private List<PublicacionQuererAdoptarConComodidades> publicacionesIntencionAdoptar = new ArrayList<>();

    public void agregar(PublicacionQuererAdoptarConComodidades publicacionIntencionAdoptar) {
        publicacionesIntencionAdoptar.add(publicacionIntencionAdoptar);
    }

    public List<PublicacionQuererAdoptarConComodidades> getPublicacionesIntencionAdoptar() {
        return publicacionesIntencionAdoptar;
    }

}
