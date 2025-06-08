package domain.repositorios;

import domain.entidades.mascota.Mascota;
import domain.entidades.publicacion.PublicacionMascotaPerdida;
import domain.repositorios.daos.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioMascotaPerdida extends Repositorio<PublicacionMascotaPerdida> {

    private List<PublicacionMascotaPerdida> mascotasPerdidas;

    public RepositorioMascotaPerdida(){
        this.mascotasPerdidas = new ArrayList<>();
    }

    public void  agregar(PublicacionMascotaPerdida mascota) {
        mascotasPerdidas.add(mascota);
    }

    public RepositorioMascotaPerdida(DAO<PublicacionMascotaPerdida> dao) {
        super(dao);
    }

}
