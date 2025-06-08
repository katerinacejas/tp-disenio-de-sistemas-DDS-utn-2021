package domain.controllers;

import domain.entidades.apiRefugios.entities.Hogar;
import domain.entidades.apiRefugios.entities.InfoHogares;
import domain.entidades.mascota.GeneracionQR;
import domain.entidades.mascota.Mascota;
import domain.entidades.publicacion.PublicacionAdoptar;
import domain.entidades.publicacion.PublicacionDarEnAdopcion;
import domain.entidades.publicacion.PublicacionMascotaPerdida;
import domain.entidades.usuario.Administrador;
import domain.entidades.usuario.Usuario;
import domain.repositorios.Repositorio;
import domain.repositorios.RepositorioAdministrador;
import domain.repositorios.RepositorioUsuario;
import domain.repositorios.RepositorioVoluntario;
import domain.repositorios.daos.DAO;
import domain.repositorios.factories.FactoryRepositorio;
import domain.repositorios.factories.FactoryRepositorioAdministrador;
import domain.repositorios.factories.FactoryRepositorioUsuarios;
import domain.repositorios.factories.FactoryRepositorioVoluntarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginController {

    private Repositorio<Mascota> repositorioMascota;
    private Repositorio<InfoHogares> repositorioHogar;
    private RepositorioUsuario repositorioUsuario = FactoryRepositorioUsuarios.get();
    private RepositorioVoluntario repositorioVoluntario = FactoryRepositorioVoluntarios.get();
    private RepositorioAdministrador repositorioAdministrador = FactoryRepositorioAdministrador.get();
    private Repositorio<PublicacionMascotaPerdida> repositorioPublicacionMascotaPerdida;
    private Repositorio<PublicacionAdoptar> repositorioPublicacionAdoptar;

    public LoginController() {
        this.repositorioMascota = FactoryRepositorio.get(Mascota.class);
        this.repositorioHogar = FactoryRepositorio.get(InfoHogares.class);
        this.repositorioPublicacionMascotaPerdida =  FactoryRepositorio.get(PublicacionMascotaPerdida.class);
        this.repositorioPublicacionAdoptar =  FactoryRepositorio.get(PublicacionAdoptar.class);
    }

    public ModelAndView entrada(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        request.session().removeAttribute("usuarioLogeado");
        request.session().removeAttribute("voluntarioLogeado");
        request.session().removeAttribute("AdminLogeado");
       
        return new ModelAndView(parametros, "elegirInicio.hbs");
    }

    public ModelAndView inicio(Request request, Response response) throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        request.session().removeAttribute("usuarioLogeado");
        String tipo0 = request.queryParams("Uname");
        if(tipo0 == null) {
            return new ModelAndView(parametros, "/inicioDeSesion.hbs");
        }
        else{
            String nombreUsuario = request.queryParams("Uname");
            String contrasenia = request.queryParams("Pass");
            if(this.repositorioUsuario.validarUsuario(nombreUsuario, contrasenia)){
                request.session().attribute("usuarioLogeado",nombreUsuario);
                //return new ModelAndView(parametros,"index.hbs");
                response.redirect("/index");
            } else if (!this.repositorioUsuario.validarUsuario(nombreUsuario, contrasenia)) {
                return new ModelAndView(parametros,"usuarioInexistente.hbs");
            }

            return new ModelAndView(parametros,"/inicioDeSesion.hbs");
        }
    }

    public ModelAndView inicioVoluntario(Request request, Response response) throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        String tipo0 = request.queryParams("Uname");
        if(tipo0 == null) {
            return new ModelAndView(parametros, "/inicioDeSesionVoluntario.hbs");
        }
        else{
            String nombreUsuario = request.queryParams("Uname");
            String contrasenia = request.queryParams("Pass");
            if(this.repositorioVoluntario.validarUsuario(nombreUsuario, contrasenia)){
                request.session().attribute("voluntarioLogeado",nombreUsuario);
                //return new ModelAndView(parametros,"index.hbs");
                response.redirect("/verPublicacionesMascotasPerdidas");
                //TODO index voluntario
            } else if (!this.repositorioVoluntario.validarUsuario(nombreUsuario, contrasenia)) {
                return new ModelAndView(parametros,"voluntarioInexistente.hbs");
            }

            return new ModelAndView(parametros,"/inicioDeSesionVoluntario.hbs");
        }
    }

    public ModelAndView inicioAdmin(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();

        String tipo0 = request.queryParams("Usuario");
        if(tipo0 == null) {
            return new ModelAndView(parametros, "/inicioDeSesionAdmin.hbs");
        }
        else{
            String nombreUsuario = request.queryParams("Usuario");
            String contrasenia = request.queryParams("Contraseña");

            if(this.repositorioAdministrador.validarUsuario(nombreUsuario, contrasenia)){

                if(repositorioAdministrador.buscarAdmin(nombreUsuario).getOrganizacion() == null) {
                    //return new ModelAndView(parametros,"index.hbs");
                    response.redirect("/mascotasAdministrador");
                } else {
                    response.redirect("/organizacionAdministradorUnico");
                }

                request.session().attribute("AdminLogeado",nombreUsuario);
            } else if (!this.repositorioVoluntario.validarUsuario(nombreUsuario, contrasenia)) {
                return new ModelAndView(parametros,"adminInexistente.hbs");
            }

            return new ModelAndView(parametros,"/inicioDeSesionAdmin.hbs");
        }
    }


    public ModelAndView index(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        List<Mascota> mascotas = this.repositorioMascota.buscarTodos();
        List<PublicacionMascotaPerdida> publicacionMascotasPerdidas = this.repositorioPublicacionMascotaPerdida.buscarTodos();
        List<PublicacionAdoptar> publicacionAdoptar = this.repositorioPublicacionAdoptar.buscarTodos();
        List<InfoHogares> hogares = this.repositorioHogar.buscarTodos();
        parametros.put("mascota", mascotas);
        parametros.put("hogar", hogares);
        parametros.put("publicacionMascotasPerdidas", publicacionMascotasPerdidas);
        parametros.put("publicacionAdoptar", publicacionAdoptar);
        return new ModelAndView(parametros,"index.hbs");
    }

    public ModelAndView indexSU(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        List<Mascota> mascotas = this.repositorioMascota.buscarTodos();
        List<InfoHogares> hogares = this.repositorioHogar.buscarTodos();
        List<PublicacionMascotaPerdida> publicacionMascotasPerdidas = this.repositorioPublicacionMascotaPerdida.buscarTodos();
        List<PublicacionAdoptar> publicacionAdoptar = this.repositorioPublicacionAdoptar.buscarTodos();
        parametros.put("mascota", mascotas);
        parametros.put("hogar", hogares);
        parametros.put("publicacionMascotasPerdidas", publicacionMascotasPerdidas);
        parametros.put("publicacionAdoptar", publicacionAdoptar);
        return new ModelAndView(parametros,"paginaPrincipalSinUsuario.hbs");
    }

    public ModelAndView registroExitoso(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "registroExitosoUsuario.hbs");
    }

    public ModelAndView usuarioInexistente(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "usuarioInexistente.hbs");
    }


    public ModelAndView voluntarioInexistente(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "voluntarioInexistente.hbs");
    }

    public ModelAndView esMiMascotaConExito(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "esMiMascotaConExito.hbs");
    }
    public ModelAndView adopcionExitosa(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "adopcionExitosa.hbs");
    }
    public ModelAndView usuarioExistente(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "usuarioExistente.hbs");
    }
    public ModelAndView seguridadContrasenia(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "seguridadContrasenia.hbs");
    }

}
