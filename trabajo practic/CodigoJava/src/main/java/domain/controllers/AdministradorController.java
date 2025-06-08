package domain.controllers;
import domain.entidades.mascota.CaracteristicaDeMascotas;
import domain.entidades.mascota.Mascota;
import domain.entidades.organizacion.Organizacion;
import domain.entidades.publicacion.PreguntaDarEnAdopcion;
import domain.entidades.publicacion.PreguntaObligatoria;
import domain.entidades.publicacion.PublicacionMascotaPerdida;
import domain.entidades.usuario.SolicitudesAdministrador;
import domain.entidades.usuario.TipoDeDocumento;
import domain.entidades.usuario.Usuario;
import domain.repositorios.Repositorio;
import domain.repositorios.RepositorioAdministrador;
import domain.entidades.usuario.Administrador;
import domain.repositorios.RepositorioVoluntario;
import domain.repositorios.factories.FactoryRepositorio;
import domain.repositorios.factories.FactoryRepositorioAdministrador;
import domain.repositorios.factories.FactoryRepositorioVoluntarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdministradorController {
    private static AdministradorController instancia;

    private Repositorio<Organizacion> repositorio;
    private Repositorio<SolicitudesAdministrador> repositorioSolicitudes;
    private Repositorio<CaracteristicaDeMascotas> repositorioCaracteristicas;
    private RepositorioAdministrador repoAdministrador = FactoryRepositorioAdministrador.get();
    private Repositorio<Administrador> repositorioAdministrador;
    private Repositorio<PreguntaObligatoria> repositorioPreguntas;



    public AdministradorController() {
        this.repositorio = FactoryRepositorio.get(Organizacion.class);
        this.repositorioSolicitudes = FactoryRepositorio.get(SolicitudesAdministrador.class);
        this.repositorioCaracteristicas = FactoryRepositorio.get(CaracteristicaDeMascotas.class);
        this.repositorioAdministrador = FactoryRepositorio.get(Administrador.class);
        this.repositorioPreguntas = FactoryRepositorio.get(PreguntaObligatoria.class);
    }

    public static AdministradorController getInstancia() {
        if (AdministradorController.instancia == null) {
            instancia = new AdministradorController();
        }
        return instancia;
    }


    public ModelAndView organizaciones(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        List<Organizacion> organizaciones = this.repositorio.buscarTodos();
        parametros.put("organizacion", organizaciones);
        return new ModelAndView(parametros,"/organizacionesAdministrador.hbs");
    }

    public ModelAndView mascotasAdministrador(Request request, Response response) throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        List<CaracteristicaDeMascotas> caracteristicas = this.repositorioCaracteristicas.buscarTodos();
        parametros.put("caracteristicademascotas", caracteristicas);

        CaracteristicaDeMascotas carac = new CaracteristicaDeMascotas();
        String nuevaCaracteristica = request.queryParams("característica");

        if (nuevaCaracteristica != null) {
            carac.setCaracteristica(nuevaCaracteristica);
            caracteristicas.add(carac);
            repositorioCaracteristicas.agregar(carac);
        }

        return new ModelAndView(parametros, "/mascotasAdministrador.hbs");
    }

    public ModelAndView solicitudesAdministrador(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        List<SolicitudesAdministrador> solicitudesAdministrador = this.repositorioSolicitudes.buscarTodos();
        parametros.put("solicitudesAdministrador", solicitudesAdministrador);
        return new ModelAndView(parametros,"/solicitudesAdministrador.hbs");
    }

    public ModelAndView registrarOrganizacion(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();

        String tipo0 = request.queryParams("nombre");

        if(tipo0 == null) {
            return new ModelAndView(parametros, "/registrarNuevaOrganizacion.hbs");

        }else{

            Organizacion organizacion = new Organizacion();

            String nombre = request.queryParams("nombre");
            String domicilio =  request.queryParams("domicilio");
            String descripcion = request.queryParams("descripcion");
            //String foto = request.queryParams("foto");

            organizacion.setDescripcion(descripcion);
            organizacion.setRutaFoto("../img/organizacionesAdministrador/resources/org1.png");
            organizacion.setNombre(nombre);
            organizacion.setDomicilio(domicilio);

            repositorio.agregar(organizacion);
            return new ModelAndView(parametros, "/organizacionRegistradaConExito.hbs");

        }
    }

    public ModelAndView registroOrganizacionExitoso(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "organizacionRegistradaConExito.hbs");
    }

    public ModelAndView registrarAdministrador(Request request, Response response) throws Exception {
        Map<String, Object> parametros = new HashMap<>();

        String tipo0 = request.queryParams("nombre");

        if (tipo0 == null) {

            return new ModelAndView(parametros, "/registrarAdministrador.hbs");

        } else {
            Administrador administrador = new Administrador();
            String nombre = request.queryParams("nombre");
            String apellido = request.queryParams("apellido");
            String nombreUsuario = request.queryParams("nombreUsuario");
            String contrasenia1 = request.queryParams("contrasenia1");
            String contrasenia2 = request.queryParams("contrasenia2");
            String email = request.queryParams("email");
            String domicilio = request.queryParams("domicilio");
            String fechaDeNacimiento = request.queryParams("fechaDeNacimiento");
            String tipoDocumentacion = request.queryParams("tipoDocumentacion");
            String documento = request.queryParams("documento");
            String id = request.queryParams("id");

            administrador.setNombre(nombre);
            administrador.setApellido(apellido);
            administrador.setUsuario(nombreUsuario);

            Organizacion organizacion = repositorio.buscar(new Integer(request.params("id")));
            administrador.setOrganizacion(organizacion);

            if (!contrasenia1.equals(contrasenia2)) {
                throw new Exception("Las contraseñas no coinciden.");
            }
            if (administrador.validarContrasenia(contrasenia1)) {
                administrador.setContrasenia(contrasenia1);
            } else {
                throw new Exception("Contraseña no válida, ingrese una contraseña mas segura! (con al menos una mayuscula)");
            }

            administrador.setEmail(email);
            administrador.setDomicilio(domicilio);
            switch (tipoDocumentacion) {
                case "DNI":
                    administrador.setTipoDeDocumento(TipoDeDocumento.DNI);
                    break;
                case "LIBRETA_CIVICA":
                    administrador.setTipoDeDocumento(TipoDeDocumento.LIBRETA_CIVICA);
                    break;
                case "LIBRETA_ENROLAMIENTO":
                    administrador.setTipoDeDocumento(TipoDeDocumento.LIBRETA_ENROLAMIENTO);
                    break;
                default:
                    administrador.setTipoDeDocumento(TipoDeDocumento.CEDULA);
                    break;
            }

            administrador.setDocumento(documento);

            repositorioAdministrador.agregar(administrador);

            response.redirect("/organizacionesAdministrador");
        }
        return null;
    }

    public ModelAndView administradorOrganizacion(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "/administradorOrganizacion.hbs");
    }

    public ModelAndView organizacionAdministradorUnico(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        List<Organizacion> organizaciones = this.repositorio.buscarTodos();

        String nombreAdmin = request.session().attribute("AdminLogeado");
        Organizacion organizacionAdmin = this.repoAdministrador.buscarAdmin(nombreAdmin).getOrganizacion();

        parametros.put("organizacion", organizacionAdmin);

        return new ModelAndView(parametros, "/organizacionAdministradorUnico.hbs");
    }

    public ModelAndView gestionarPreguntas(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();

        String pregunta = request.queryParams("pregunta");

        String nombreAdmin = request.session().attribute("AdminLogeado");
        Organizacion organizacionAdmin = this.repoAdministrador.buscarAdmin(nombreAdmin).getOrganizacion();

        if (pregunta != null) {
            PreguntaDarEnAdopcion preguntaDeOrganizacion = new PreguntaDarEnAdopcion();
            String respuesta = request.queryParams("respuesta");

            String queryString = request.queryString();
            System.out.println(queryString);
            String[] partes = queryString.split("&");

            preguntaDeOrganizacion.setOrganizacion(organizacionAdmin);

            List<String> respuestasParaAgregar = new ArrayList<>();
            for (String parte : partes) {
                String sinIgual = parte.replace("=", " ");
                String sinMas = sinIgual.replace("+", " ");
                //System.out.println(sinMas);

                if(sinMas.contains("pregunta")) {
                    int meQuedoCon = sinMas.length() - 9;
                    String preguntaIngresada = sinMas.substring(sinMas.length() - meQuedoCon);
                    System.out.println("agrego la pregunta " + preguntaIngresada + " al repositorio");
                    preguntaDeOrganizacion.setPregunta(preguntaIngresada);
                } else if(sinMas.contains("respuesta")) {
                    int meQuedoCon = sinMas.length() - 10;
                    String respuestaIngresada = sinMas.substring(sinMas.length() - meQuedoCon);
                    System.out.println("agrego la respesta " + respuestaIngresada +" al repositorio");
                    respuestasParaAgregar.add(respuestaIngresada);
                }
            }

            if(respuesta == null) {
                System.out.println("agrego pregunta de respuesta libre al repositorio");
                preguntaDeOrganizacion.setEsPreguntaDeRespuestaLibre(true);

            }
            else {
                preguntaDeOrganizacion.setRespuesta(respuestasParaAgregar);
            }

            repositorioPreguntas.agregar(preguntaDeOrganizacion);

            return new ModelAndView(parametros, "/preguntasAdministrador.hbs");
        }
        return new ModelAndView(parametros, "/preguntasAdministrador.hbs");
    }

}
