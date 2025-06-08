package domain.repositorios;

import domain.entidades.apiRefugios.entities.InfoHogares;
import domain.repositorios.daos.DAO;

public class RepositorioInfoHogares extends Repositorio<InfoHogares> {

    public RepositorioInfoHogares(DAO<InfoHogares> dao) {
        super(dao);
    }

}
