package domain.repositorios;
import baseDeDatos.EntityManagerHelper;
import domain.entidades.usuario.Administrador;
import domain.repositorios.daos.DAO;
import domain.repositorios.daos.DAOHibernate;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class Repositorio<T> {
    protected DAOHibernate<T> dao;

    public Repositorio(DAO<T> dao) {
        this.dao = (DAOHibernate<T>) dao;
    }

    public Repositorio() {

    }

    public void setDao(DAO<T> dao) {
        this.dao = (DAOHibernate<T>) dao;
    }

    public void agregar(Object unObjeto){
        this.dao.agregar(unObjeto);
    }

    public void modificar(Object unObjeto){
        this.dao.modificar(unObjeto);
    }

    public void eliminar(Object unObjeto){
        this.dao.eliminar(unObjeto);
    }

    public List<T> buscarTodos(){
        return this.dao.buscarTodos();
    }

    public T buscar(int id){
        return this.dao.buscar(id);
    }

    public CriteriaBuilder criteriaBuilder(){
        return EntityManagerHelper.getEntityManager().getCriteriaBuilder();
    }


}