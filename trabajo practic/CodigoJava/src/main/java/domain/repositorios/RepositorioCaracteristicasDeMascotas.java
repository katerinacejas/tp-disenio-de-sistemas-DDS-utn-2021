package domain.repositorios;

import domain.entidades.mascota.CaracteristicaDeMascotas;
import domain.entidades.usuario.SolicitudesAdministrador;
import domain.repositorios.daos.DAO;

public class RepositorioCaracteristicasDeMascotas  extends Repositorio<CaracteristicaDeMascotas>{

    public RepositorioCaracteristicasDeMascotas(DAO<CaracteristicaDeMascotas> dao) {
        super(dao);
    }
}
