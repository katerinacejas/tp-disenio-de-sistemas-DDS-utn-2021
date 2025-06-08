package domain.controllers;

import domain.entidades.apiRefugios.ListaHogaresDeTransito;
import domain.entidades.apiRefugios.entities.*;
import controllers.exceptions.ListaDeHogaresVaciaException;
import domain.entidades.mascota.*;
import domain.entidades.organizacion.Organizacion;
import domain.repositorios.Repositorio;
import domain.repositorios.RepositorioHogares;
import domain.repositorios.RepositorioMascota;
import domain.repositorios.factories.FactoryRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HogaresController {
    private static HogaresController instancia;
    public ListaHogaresDeTransito listaHogaresDeTransito;
    public RepositorioHogares repositorioHogares = new RepositorioHogares();
    public List<Hogar> hogaresGuardados = new ArrayList<>();
    private Repositorio<Hogar> repositorio;

    public HogaresController(){
        this.listaHogaresDeTransito = new ListaHogaresDeTransito();
        this.repositorio = FactoryRepositorio.get(Hogar.class);
    }

    public static HogaresController getInstancia() {
        if (HogaresController.instancia == null) {
            instancia = new HogaresController();
        }
        return instancia;
    }

    public void cargarListaHogares() throws IOException {
        hogaresGuardados = this.listaHogaresDeTransito.actualizarListaHgares();
        if(hogaresGuardados.isEmpty()){
            throw new ListaDeHogaresVaciaException("Lista de Hogares Vacia, fallo la API");
        } else {
            this.repositorioHogares.setListaDeHogares(hogaresGuardados);
        }
    }

    public ModelAndView hogaresTransito(Request request, Response response) throws IOException {
        Map<String, Object> parametros = new HashMap<>();
        this.cargarListaHogares();
        List<Hogar> hogares = this.repositorioHogares.getListaDeHogares();
        parametros.put("hogar", hogares);

        if(request.session().attribute("usuarioLogeado") == null) {
            return new ModelAndView(parametros, "hogaresTransitoSinUsuario.hbs");
        } else {
            return new ModelAndView(parametros,"hogaresTransito.hbs");

        }
    }

    public List<Hogar> retornarHogaresFiltrados(List<Filter> filters) throws IOException {

        this.cargarListaHogares();
        List<Hogar> hogares = this.repositorioHogares.getListaDeHogares();

        FilterHogares filterHogares = new FilterHogares(filters);

       return filterHogares.filtrarHogares(hogares);
    }

}
