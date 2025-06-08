package domain.repositorios.factories;

import domain.entidades.usuario.Administrador;
import domain.repositorios.RepositorioAdministrador;
import domain.repositorios.daos.DAO;
import domain.repositorios.daos.DAOHibernate;

public class FactoryRepositorioAdministrador {
    private static RepositorioAdministrador  repo;

    static {
        repo = null;
    }

    public static RepositorioAdministrador  get(){
        if(repo == null){
            DAO<Administrador> dao = new DAOHibernate<>(Administrador.class);
            repo = new RepositorioAdministrador(dao);
        }
        return repo;
    }
}