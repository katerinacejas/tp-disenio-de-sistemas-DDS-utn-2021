package domain.repositorios;

import domain.entidades.contrasenia.Contrasenia;
import domain.entidades.usuario.Usuario;
import domain.entidades.usuario.Voluntario;
import domain.repositorios.daos.DAO;

import java.util.ArrayList;
import java.util.List;

public class RepositorioVoluntario extends Repositorio<Voluntario>{

    public RepositorioVoluntario(DAO<Voluntario> dao) {
        super(dao);
    }

    public List<Voluntario> voluntarios = new ArrayList<>();

    public boolean existeVoluntario(String nombreUsuario) {
        List<Voluntario> listaVoluntarios = super.buscarTodos();
        for (Voluntario voluntario : listaVoluntarios) {
            if(nombreUsuario.equals(voluntario.getUsuario())) {
                return true;
            }
        }
        return false;
    }

    public boolean validarUsuario(String nombreUsuario, String contrasenia) {
        if(this.existeVoluntario(nombreUsuario)) {
            List<Voluntario> todosVoluntarios = super.buscarTodos();
            for(Voluntario voluntario : todosVoluntarios) {
                Contrasenia contrasenia2 = new Contrasenia();
                String contraHasheada = contrasenia2.contraseniaHash(contrasenia);
                if(voluntario.getUsuario().equals(nombreUsuario)){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

}
