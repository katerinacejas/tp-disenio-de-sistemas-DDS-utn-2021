package domain.controllers;

import domain.entidades.mascota.Mascota;
import domain.entidades.organizacion.Organizacion;
import domain.entidades.publicacion.PublicacionAdoptar;
import domain.entidades.publicacion.PublicacionDarEnAdopcion;
import domain.entidades.publicacion.PublicacionMascotaPerdida;
import domain.entidades.publicacion.PublicacionQuererAdoptarConComodidades;
import domain.entidades.usuario.*;
import domain.entidades.usuario.formasNotificacion.EstrategiaDeNotificacion;
import domain.entidades.usuario.formasNotificacion.Mail;
import domain.entidades.usuario.formasNotificacion.SMS;
import domain.entidades.usuario.formasNotificacion.Whatsapp;
import domain.repositorios.*;
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

public class VoluntarioController {

    private RepositorioVoluntario repositorio = FactoryRepositorioVoluntarios.get();
    private Repositorio<PublicacionMascotaPerdida> repositorioPublicacionPerdida;
    private Repositorio<PublicacionDarEnAdopcion> repositorioPublicacionDarEnAdopcion;
    private Repositorio<PublicacionQuererAdoptarConComodidades> repositorioPublicacionIntencionDeAdoptar;
    private RepositorioAdministrador repositorioAdministrador = FactoryRepositorioAdministrador.get();

    public VoluntarioController() {
        this.repositorioPublicacionPerdida = FactoryRepositorio.get(PublicacionMascotaPerdida.class);
        this.repositorioPublicacionDarEnAdopcion = FactoryRepositorio.get(PublicacionDarEnAdopcion.class);
        this.repositorioPublicacionIntencionDeAdoptar = FactoryRepositorio.get(PublicacionQuererAdoptarConComodidades.class);
    }
    

    public ModelAndView registrarVoluntario(Request request, Response response) throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        String tipo0 = request.queryParams("nombre");

        if (tipo0 == null) {

            return new ModelAndView(parametros, "/registrarVoluntario.hbs");

        } else {
            Voluntario voluntario = new Voluntario();
            String nombre = request.queryParams("nombre");
            String apellido = request.queryParams("apellido");
            String nombreUsuario = request.queryParams("nombreUsuario");
            String contrasenia1 = request.queryParams("contrasenia1");
            String contrasenia2 = request.queryParams("contrasenia2");
            String email = request.queryParams("email");
            String fechaDeNacimiento = request.queryParams("fechaDeNacimiento");
            String tipoDocumentacion = request.queryParams("tipoDocumentacion");
            String documento = request.queryParams("documento");

            voluntario.setNombre(nombre);
            voluntario.setApellido(apellido);
            voluntario.setUsuario(nombreUsuario);

            if (!contrasenia1.equals(contrasenia2)) {
                throw new Exception("Las contraseñas no coinciden.");
            }
            if (this.repositorio.existeVoluntario(nombreUsuario)) {
                throw new Exception("Este voluntario ya ha sido registrado. Intente otro nombre de usuario.");
            }
            if (voluntario.validarContrasenia(contrasenia1)) {
                voluntario.setContrasenia(contrasenia1);
            } else {
                throw new Exception("Contraseña no válida, ingrese una contraseña mas segura! (con al menos una mayuscula)");
            }

            voluntario.setEmail(email);
            // usuario.setFechaDeNacimiento(fechaDeNacimiento); cambiar a DATE

            switch (tipoDocumentacion) {
                case "DNI":
                    voluntario.setTipoDeDocumento(TipoDeDocumento.DNI);
                    break;
                case "LIBRETA_CIVICA":
                    voluntario.setTipoDeDocumento(TipoDeDocumento.LIBRETA_CIVICA);
                    break;
                case "LIBRETA_ENROLAMIENTO":
                    voluntario.setTipoDeDocumento(TipoDeDocumento.LIBRETA_ENROLAMIENTO);
                    break;
                default:
                    voluntario.setTipoDeDocumento(TipoDeDocumento.CEDULA);
                    break;
            }

            voluntario.setDocumento(documento);


            repositorio.agregar(voluntario);
            response.redirect( "/registrarVoluntario");
            return null;
        }
    }

    public ModelAndView registrarVoluntarioOrganizacion(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();

        String tipo0 = request.queryParams("nombre");

        if (tipo0 == null) {

            return new ModelAndView(parametros, "/registrarVoluntarioAdminUnico.hbs");

        } else {
            Voluntario voluntario = new Voluntario();
            String nombre = request.queryParams("nombre");
            String apellido = request.queryParams("apellido");
            String nombreUsuario = request.queryParams("nombreUsuario");
            String contrasenia1 = request.queryParams("contrasenia1");
            String contrasenia2 = request.queryParams("contrasenia2");
            String email = request.queryParams("email");
            String fechaDeNacimiento = request.queryParams("fechaDeNacimiento");
            String tipoDocumentacion = request.queryParams("tipoDocumentacion");
            String documento = request.queryParams("documento");

            voluntario.setNombre(nombre);
            voluntario.setApellido(apellido);
            voluntario.setUsuario(nombreUsuario);

            voluntario.setEmail(email);
            // usuario.setFechaDeNacimiento(fechaDeNacimiento); cambiar a DATE

            switch (tipoDocumentacion) {
                case "DNI":
                    voluntario.setTipoDeDocumento(TipoDeDocumento.DNI);
                    break;
                case "LIBRETA_CIVICA":
                    voluntario.setTipoDeDocumento(TipoDeDocumento.LIBRETA_CIVICA);
                    break;
                case "LIBRETA_ENROLAMIENTO":
                    voluntario.setTipoDeDocumento(TipoDeDocumento.LIBRETA_ENROLAMIENTO);
                    break;
                default:
                    voluntario.setTipoDeDocumento(TipoDeDocumento.CEDULA);
                    break;
            }

            voluntario.setDocumento(documento);

            String usuarioAdmin = request.session().attribute("AdminLogeado");
            voluntario.setOrganizacion(this.repositorioAdministrador.buscarAdmin(usuarioAdmin).getOrganizacion());


            repositorio.agregar(voluntario);
            response.redirect( "/registrarVoluntarioAdminUnico");
            return null;
        }
    }

    public ModelAndView verPublicacionesMascotasPerdidas(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();

        List<PublicacionMascotaPerdida> publicacionAdoptar = this.repositorioPublicacionPerdida.buscarTodos();
        parametros.put("publicacionMascotasPerdidas", publicacionAdoptar);

        return new ModelAndView(parametros,"/verPublicacionesMascotasPerdidas.hbs");
    }

    public ModelAndView verPublicacionesDarEnAdopcion(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();

        List<PublicacionDarEnAdopcion> publicacionDarEnAdopcion = this.repositorioPublicacionDarEnAdopcion.buscarTodos();
        parametros.put("publicacionDarEnAdopcion", publicacionDarEnAdopcion);

        return new ModelAndView(parametros,"/verPublicacionesDarEnAdopcion.hbs");
    }

    public ModelAndView verPublicacionesIntencionDeAdoptar(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();

        List<PublicacionQuererAdoptarConComodidades> publicacionIntencionDeAdoptar = this.repositorioPublicacionIntencionDeAdoptar.buscarTodos();
        parametros.put("publicacionIntencionDeAdoptar", publicacionIntencionDeAdoptar);

        return new ModelAndView(parametros,"/verPublicacionesIntencionDeAdoptar.hbs");
    }


    public ModelAndView aprobarPublicacionMascotaPerdida(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        PublicacionMascotaPerdida publicacion = this.repositorioPublicacionPerdida.buscar(new Integer(request.params("id")));
        parametros.put("publicacionMascotasPerdidas", publicacion);
        String prueba = request.queryParams("estado");
        if (prueba != null) {
            String estado = request.queryParams("estado");
            if (estado.equals("aprobado")) {
                publicacion.setAprobada(true);
            } else if (estado.equals("desaprobado")) {
                publicacion.setAprobada(false);
            }
            this.repositorioPublicacionPerdida.modificar(publicacion);
            return new ModelAndView(parametros,"/aprobarPublicacionMascotaPerdidaExitoso.hbs");
        }
        return new ModelAndView(parametros,"/aprobarPublicacionMascotaPerdida.hbs");
    }

    public ModelAndView aprobarPublicacionIntencionDeAdoptar(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        PublicacionQuererAdoptarConComodidades publicacion = this.repositorioPublicacionIntencionDeAdoptar.buscar(new Integer(request.params("id")));
        parametros.put("publicacionIntencionDeAdoptar", publicacion);
        String prueba = request.queryParams("estado");
        if (prueba != null) {
            String estado = request.queryParams("estado");
            if (estado.equals("aprobado")) {
                publicacion.setAprobada(true);
            } else if (estado.equals("desaprobado")) {
                publicacion.setAprobada(false);
            }
            this.repositorioPublicacionIntencionDeAdoptar.modificar(publicacion);
            return new ModelAndView(parametros,"/aprobarPublicacionIntencionDeAdoptarExitoso.hbs");
        }
        return new ModelAndView(parametros,"/aprobarPublicacionIntencionDeAdoptar.hbs");
    }

    public ModelAndView aprobarPublicacionDarEnAdopcion(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        PublicacionDarEnAdopcion publicacion = this.repositorioPublicacionDarEnAdopcion.buscar(new Integer(request.params("id")));
        parametros.put("publicacionDarEnAdopcion", publicacion);
        String prueba = request.queryParams("estado");
        if (prueba != null) {
            String estado = request.queryParams("estado");
            if (estado.equals("aprobado")) {
                publicacion.setAprobada(true);
            } else if (estado.equals("desaprobado")) {
                publicacion.setAprobada(false);
            }
            this.repositorioPublicacionDarEnAdopcion.modificar(publicacion);
            return new ModelAndView(parametros,"/aprobarPublicacionDarEnAdopcionExitoso.hbs");
        }
        return new ModelAndView(parametros,"/aprobarPublicacionDarEnAdopcion.hbs");
    }

}
