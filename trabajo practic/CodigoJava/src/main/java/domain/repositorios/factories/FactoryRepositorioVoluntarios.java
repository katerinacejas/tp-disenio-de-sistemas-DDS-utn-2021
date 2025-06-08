package domain.repositorios.factories;

import domain.entidades.usuario.Usuario;
import domain.entidades.usuario.Voluntario;
import domain.repositorios.RepositorioUsuario;
import domain.repositorios.RepositorioVoluntario;
import domain.repositorios.daos.DAO;
import domain.repositorios.daos.DAOHibernate;

public class FactoryRepositorioVoluntarios {
    private static RepositorioVoluntario repo;

    static {
        repo = null;
    }

    public static RepositorioVoluntario get(){
        if(repo == null){
            DAO<Voluntario> dao = new DAOHibernate<>(Voluntario.class);
            repo = new RepositorioVoluntario(dao);
        }
        return repo;
    }
}
