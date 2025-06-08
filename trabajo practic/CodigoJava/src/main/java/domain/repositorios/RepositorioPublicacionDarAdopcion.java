package domain.repositorios;

import domain.entidades.publicacion.PublicacionDarEnAdopcion;

import java.util.ArrayList;
import java.util.List;

public class RepositorioPublicacionDarAdopcion {

    private List<PublicacionDarEnAdopcion> publicacionDarEnAdopcionList = new ArrayList<>();


    public void agregar(PublicacionDarEnAdopcion darEnAdopcion) {
        publicacionDarEnAdopcionList.add(darEnAdopcion);

    }
}
