package domain.repositorios;
import domain.entidades.organizacion.Organizacion;
import domain.repositorios.daos.DAO;
import java.util.List;
public class RepositorioOrganizacion extends Repositorio<Organizacion> {
    private List<Organizacion> organizaciones;
    public RepositorioOrganizacion(DAO<Organizacion> dao) {
        super(dao);
    }
    public void agregar(Organizacion organizacion) {
        organizaciones.add(organizacion);
    }
}
