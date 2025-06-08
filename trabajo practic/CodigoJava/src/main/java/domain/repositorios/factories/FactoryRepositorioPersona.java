package domain.repositorios.factories;

import domain.entidades.usuario.Persona;
import domain.entidades.usuario.Usuario;
import domain.repositorios.RepositorioPersona;
import domain.repositorios.RepositorioUsuario;
import domain.repositorios.daos.DAO;
import domain.repositorios.daos.DAOHibernate;

public class FactoryRepositorioPersona {

    private static RepositorioPersona repo;

    static {
        repo = null;
    }

    public static RepositorioPersona get(){
        if(repo == null){
            DAO<Persona> dao = new DAOHibernate<>(Persona.class);
            repo = new RepositorioPersona(dao);
        }
        return repo;
    }

}
