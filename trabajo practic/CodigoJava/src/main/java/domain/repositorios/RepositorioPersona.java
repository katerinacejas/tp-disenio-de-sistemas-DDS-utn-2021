package domain.repositorios;
import domain.entidades.usuario.Persona;
import domain.repositorios.daos.DAO;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPersona extends Repositorio<Persona> {

    public RepositorioPersona(DAO<Persona> dao) {
        super(dao);
    }

    private List<Persona> personas;

    public RepositorioPersona(){
        this.personas = new ArrayList<>();
    }

    public void agregar(Persona unaPersona) {
        personas.add(unaPersona);
    }

    public boolean validarPersona(String documento, String tramite) {
        List<Persona> listaPersonas = super.buscarTodos();
        for(Persona persona : listaPersonas) {
            if(documento.equals(persona.getDocumento()) && tramite.equals(persona.getDomicilio())) {
                return true;
            }
        }
        return false;
    }

    public Persona obtenerPersona(String documento, String tramite) {
        List<Persona> listaPersonas = super.buscarTodos();
        for(Persona persona : listaPersonas) {
            if(documento.equals(persona.getDocumento()) && tramite.equals(persona.getDomicilio())) {
                return persona;
            }
        }
        return null;
    }
}
