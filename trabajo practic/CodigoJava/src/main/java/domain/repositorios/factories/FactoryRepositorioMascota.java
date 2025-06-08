package domain.repositorios.factories;


import domain.entidades.mascota.Mascota;
import domain.entidades.usuario.Usuario;
import domain.repositorios.RepositorioMascota;
import domain.repositorios.RepositorioUsuario;
import domain.repositorios.daos.DAO;
import domain.repositorios.daos.DAOHibernate;

public class FactoryRepositorioMascota {

    private static RepositorioMascota repo;

    public static RepositorioMascota get(){
        if(repo == null){
            DAO<Mascota> dao = new DAOHibernate<>(Mascota.class);
            repo = new RepositorioMascota(dao);
        }
        return repo;
    }

}
