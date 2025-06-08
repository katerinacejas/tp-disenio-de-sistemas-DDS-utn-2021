package domain.repositorios;

import domain.entidades.apiRefugios.entities.Hogar;
import domain.repositorios.daos.DAO;

import java.util.ArrayList;
import java.util.List;

public class RepositorioHogares extends Repositorio<Hogar> {

    public List<Hogar> listaDeHogares = new ArrayList<>();

    public RepositorioHogares(DAO<Hogar> dao) {
        super(dao);
    }

    public RepositorioHogares() {
        super();
    }


    public void setListaDeHogares(List<Hogar> listaDeHogares) {
        this.listaDeHogares = listaDeHogares;
    }

    public List<Hogar> getListaDeHogares() {
        return listaDeHogares;
    }

    public void mostrarLista(){
        for (Hogar unHogar : listaDeHogares) {
            System.out.println(unHogar.getNombre());
        }
        System.out.println(listaDeHogares.size());
    }
}
