package domain.repositorios;

import domain.entidades.usuario.Rescatista;

import java.util.ArrayList;
import java.util.List;

public class RepositorioRescatista {

    public List<Rescatista> rescatistas;

    public RepositorioRescatista(){
        this.rescatistas = new ArrayList<>();
    }

    public void agregar(Rescatista unRescatista){
        rescatistas.add(unRescatista);
    }

}