package domain.repositorios;

import domain.entidades.mascota.Mascota;
import domain.entidades.usuario.SolicitudesAdministrador;
import domain.repositorios.daos.DAO;

public class RepositorioSolicitudesAdministrador extends Repositorio<SolicitudesAdministrador>{

    public RepositorioSolicitudesAdministrador(DAO<SolicitudesAdministrador> dao) {
        super(dao);
    }

}
