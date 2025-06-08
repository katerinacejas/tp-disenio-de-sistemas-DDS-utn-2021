package domain.repositorios;

import domain.entidades.apiRefugios.entities.Filter;
import domain.entidades.apiRefugios.entities.InfoHogares;
import domain.entidades.contrasenia.Contrasenia;
import domain.entidades.usuario.Usuario;
import domain.repositorios.daos.DAO;
import domain.repositorios.daos.DAOHibernate;
import domain.repositorios.factories.FactoryRepositorio;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuario extends Repositorio<Usuario> {

    public RepositorioUsuario(DAO<Usuario> dao) {
        super(dao);
    }

    public boolean existeUsuario(String nombreUsuario) {
        List<Usuario> listaUsuarios = super.buscarTodos();
        for (Usuario usuario : listaUsuarios) {
            if(nombreUsuario.equals(usuario.getUsuario())) {
                return true;
            }
        }
        return false;
    }

    public boolean existeDNIyNumeroDeTramiteSinUsuario(String numeroDocumento, String numeroDeTramite) {
        List<Usuario> listaUsuarios = super.buscarTodos();
        for (Usuario usuario : listaUsuarios) {
            if(numeroDocumento.equals(usuario.getDocumento()) && numeroDeTramite.equals(usuario.getnTramite())){
                return true;
            }
        }
        return false;
    }

    public boolean validarUsuario(String nombreUsuario, String contrasenia) {
        if(this.existeUsuario(nombreUsuario)) {
            List<Usuario> todosUsuarios = super.buscarTodos();
            for(Usuario usuario : todosUsuarios) {
                Contrasenia contrasenia2 = new Contrasenia();
                String contraHasheada = contrasenia2.contraseniaHash(contrasenia);
                if(usuario.getContrasenia() != null && usuario.getContrasenia().equals(contraHasheada)  ){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public int usuarioID(String nombreUsuario) {
        List<Usuario> listaUsuarios = super.buscarTodos();
        for (Usuario usuario : listaUsuarios) {
            if (usuario != null) {
                if (nombreUsuario.equals(usuario.getUsuario())) {
                    return usuario.getId();
                }
            }
        }
        return 0;
    }

    public int usuarioIDporDocumento(String numeroDocumento,String numeroDeTramite ) {
        List<Usuario> listaUsuarios = super.buscarTodos();
        for (Usuario usuario : listaUsuarios) {
            if(numeroDocumento.equals(usuario.getDocumento()) && numeroDeTramite.equals(usuario.getnTramite())){
                return  usuario.getId();
            }
        }
        return 13;
    }


}