package domain.repositorios.factories;

import config.Config;
import domain.entidades.usuario.Usuario;
import domain.repositorios.RepositorioUsuario;
import domain.repositorios.daos.DAO;
import domain.repositorios.daos.DAOHibernate;

public class FactoryRepositorioUsuarios {
    private static RepositorioUsuario repo;

    static {
        repo = null;
    }

    public static RepositorioUsuario get(){
        if(repo == null){
                DAO<Usuario> dao = new DAOHibernate<>(Usuario.class);
                repo = new RepositorioUsuario(dao);
        }
        return repo;
    }
}