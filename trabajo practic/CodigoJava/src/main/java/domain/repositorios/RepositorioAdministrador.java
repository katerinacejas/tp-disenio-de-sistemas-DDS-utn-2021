package domain.repositorios;

import domain.entidades.contrasenia.Contrasenia;
import domain.entidades.usuario.Administrador;
import domain.repositorios.daos.DAO;

import java.util.ArrayList;
import java.util.List;

public class RepositorioAdministrador extends Repositorio<Administrador>{
    public RepositorioAdministrador(DAO<Administrador> dao) {
        super(dao);
    }

    public List<Administrador> administrador = new ArrayList<>();

    public boolean existeAdministrador(String nombreUsuario) {
        List<Administrador> listaAdministradores = super.buscarTodos();
        for (Administrador administrador : listaAdministradores) {
            if(nombreUsuario.equals(administrador.getUsuario())) {
                return true;
            }
        }
        return false;
    }

    public boolean validarUsuario(String nombreUsuario, String contrasenia) {
        if(this.existeAdministrador(nombreUsuario)) {
            List<Administrador> todosAdministradores = super.buscarTodos();

            for(Administrador administrador : todosAdministradores) {

                //Contrasenia contrasenia2 = new Contrasenia();
                //String contraHasheada = contrasenia2.contraseniaHash(contrasenia);
                if(administrador.getUsuario().equals(nombreUsuario)){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public Administrador buscarAdmin(String nombre) {
        List<Administrador> todosAdministradores = super.buscarTodos();
        for(Administrador administrador : todosAdministradores) {
            if(administrador.getUsuario().equals(nombre)) {
                return administrador;
            }
        }
        return null;
    }


}
