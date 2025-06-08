package server;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import domain.controllers.MascotasController;
import spark.RouteGroup;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;
import domain.controllers.LoginController;
import domain.controllers.*;

import static spark.Spark.staticFiles;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }

    public static void init() {
        Router.initEngine();
        staticFiles.expireTime(600);
        staticFiles.externalLocation("imagenUsuario");
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure() {
        LoginController loginController                 = new LoginController();
        MascotasController mascotaController            = new MascotasController();
        AdministradorController administradorController = new AdministradorController();
        HogaresController hogaresController             = new HogaresController();
        InteresadoController interesadoController       = new InteresadoController();
        PersonaController personaController             = new PersonaController();
        UsuarioController usuarioController             = new UsuarioController();
        VoluntarioController voluntarioController       = new VoluntarioController();
        FotoController fotoController                   = new FotoController();
        // con usuario

        Spark.get("/",                                      loginController::entrada, Router.engine);

        Spark.get("/index",                                 loginController::index, Router.engine);

        Spark.get("/inicioDeSesion",                        loginController::inicio, Router.engine);


        Spark.get("/adopcionExitosa",                       loginController::adopcionExitosa, Router.engine);

        Spark.get("/esMiMascotaConExito",                   loginController::esMiMascotaConExito, Router.engine);

        Spark.get("/voluntarioInexistente",                 loginController::voluntarioInexistente, Router.engine);

        Spark.get("/usuarioInexistente",                    loginController::usuarioInexistente, Router.engine);

        Spark.get("/registroExitoso",                       loginController::registroExitoso, Router.engine);

        Spark.get("/usuarioExistente",                      loginController::usuarioExistente, Router.engine);

        Spark.get("/seguridadContrasenia",                  loginController::seguridadContrasenia, Router.engine);

        Spark.get("/misMascotasUsuario",                    mascotaController::misMascotas, Router.engine);

        Spark.get("/verMiMascota/:id",                      mascotaController::miMascota, Router.engine);

        Spark.get("/formularioEsMiMascota",                 mascotaController::formularioEsMiMascota, Router.engine);

        Spark.post("/formularioEsMiMascota",                mascotaController::subirFotoMascota, Router.engine);

        Spark.get("/formularioEstaPerdida",                 mascotaController::formularioEstaPerdida, Router.engine);

        Spark.get("/formularioEstaPerdidaQR",               mascotaController::formularioEstaPerdidaQR, Router.engine);

        Spark.get("/seccionRegistrarMascotas",              mascotaController::seccionRegistrarMascotas, Router.engine);

        Spark.get("/seccionRegistrarMascotas",              mascotaController::misMascotasSU, Router.engine);

        Spark.get("/adoptarUnaMascota",                     mascotaController::mostrarTodos, Router.engine);

        Spark.get("/adoptarUnaMascota",                     mascotaController::mostrarTodos, Router.engine);

        Spark.get("/adoptarMascota/:id",                    mascotaController::adoptarMascota, Router.engine);

        Spark.get("/registrarUsuario",                      usuarioController::registrarUsuario, Router.engine);

        Spark.get("/hogaresTransito",                       hogaresController::hogaresTransito, Router.engine);

        Spark.get("/hogaresTransitoSU",                     hogaresController::hogaresTransito, Router.engine);

        Spark.get("/mascotasPerdidas",                      mascotaController::mascotasPerdidas, Router.engine);

        Spark.get("/verMascotaPerdida/:id",                 mascotaController::mascotaPerdida, Router.engine);

        Spark.get("/quererAdoptarConComodidades",           interesadoController::quererAdoptarConComodidades, Router.engine);

        Spark.get("/mascotaCargadaExitosamente",            mascotaController::mostrarQR, Router.engine);

        Spark.get("/formularioQuererDarEnAdopcionMascota",  mascotaController::quererDarEnAdopcion, Router.engine);

        Spark.get("/quererDarEnAdopcionExito",              mascotaController::quererDarEnAdopcionExito, Router.engine);

        // sin usuario

        Spark.get("/sinUsuario",                            loginController::indexSU, Router.engine);

        Spark.get("/paginaPrincipalSinUsuario",             loginController::indexSU, Router.engine);

        Spark.get("/misMascotasSinUsuario",                 mascotaController::misMascotasSU, Router.engine);

        Spark.get("/seccionRegistrarMascotaSinUsuario",     mascotaController::seccionRegistrarMascotaSU, Router.engine);

        Spark.get("/formularioEstaPerdidaSinUsuario",       mascotaController::estaPerdidaaSU, Router.engine);

        Spark.get("/registrarMascotaSinUsuario",            usuarioController::registrarSINUsuario, Router.engine);

        Spark.get("/verMascotasSinUsuario",                 personaController::verMascotas, Router.engine);

        Spark.get("/mascotasPerdidasSU",                    mascotaController::mascotasPerdidasSU, Router.engine);

        Spark.get("/verMascotaPerdidaSU/:id",               mascotaController::mascotaPerdidaSU, Router.engine);

        Spark.get("/formularioEsMiMascotaSinUsuario",       mascotaController::registrarMascotaSU, Router.engine);

        Spark.post("/formularioEsMiMascotaSinUsuario",      mascotaController::subirFotoMascota, Router.engine);

        // administrador

        Spark.get("/admin",                                 loginController::inicioAdmin, Router.engine);

        Spark.get("/inicioDeSesionAdmin",                   loginController::inicioAdmin, Router.engine);

        Spark.get("/mascotasAdministrador",                 administradorController::mascotasAdministrador, Router.engine);

        Spark.get("/organizacionesAdministrador",           administradorController::organizaciones, Router.engine);

        Spark.get("/mascotasAdministrador",                 administradorController::mascotasAdministrador, Router.engine);

        Spark.get("/solicitudesAdministrador",              administradorController::solicitudesAdministrador, Router.engine);

        Spark.get("/organizacionRegistradaConExito",        administradorController::registroOrganizacionExitoso, Router.engine);

        Spark.get("/registrarNuevaOrganizacion",            administradorController::registrarOrganizacion, Router.engine);

        Spark.get("/registrarAdministrador/:id",            administradorController::registrarAdministrador, Router.engine);

        Spark.get("/administradorOrganizacion",             administradorController::administradorOrganizacion, Router.engine);

        Spark.get("/organizacionAdministradorUnico",        administradorController::organizacionAdministradorUnico, Router.engine);

        Spark.get("/preguntasAdministrador",                administradorController::gestionarPreguntas, Router.engine);

        // voluntario

        Spark.get("/verPublicacionesMascotasPerdidas",          voluntarioController::verPublicacionesMascotasPerdidas, Router.engine);

        Spark.get("/verPublicacionesIntencionDeAdoptar",        voluntarioController::verPublicacionesIntencionDeAdoptar, Router.engine);

        Spark.get("/verPublicacionesDarEnAdopcion",             voluntarioController::verPublicacionesDarEnAdopcion, Router.engine);

        Spark.get("/aprobarPublicacionMascotaPerdida/:id",      voluntarioController::aprobarPublicacionMascotaPerdida, Router.engine);

        Spark.get("/aprobarPublicacionIntencionDeAdoptar/:id",  voluntarioController::aprobarPublicacionIntencionDeAdoptar, Router.engine);

        Spark.get("/aprobarPublicacionDarEnAdopcion/:id",       voluntarioController::aprobarPublicacionDarEnAdopcion, Router.engine);

        Spark.get("/inicioDeSesionVoluntario",                  loginController::inicioVoluntario, Router.engine);

        Spark.get("/registrarVoluntario",                       voluntarioController::registrarVoluntario, Router.engine);

        Spark.get("/registrarVoluntarioAdminUnico",             voluntarioController::registrarVoluntarioOrganizacion, Router.engine);

        //TODO probando
        Spark.get("/subirFoto",fotoController::subirFoto,Router.engine);

        Spark.post("/subirFoto",fotoController::mostrarFoto,Router.engine);
    }
}