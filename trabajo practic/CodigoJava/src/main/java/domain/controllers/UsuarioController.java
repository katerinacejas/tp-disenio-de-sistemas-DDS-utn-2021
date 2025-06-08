package domain.controllers;

import domain.entidades.mascota.*;
import domain.entidades.usuario.Contacto;
import domain.entidades.usuario.TipoDeDocumento;
import domain.entidades.usuario.formasNotificacion.EstrategiaDeNotificacion;
import domain.entidades.usuario.formasNotificacion.Mail;
import domain.entidades.usuario.formasNotificacion.SMS;
import domain.entidades.usuario.formasNotificacion.Whatsapp;
import domain.repositorios.Repositorio;
import domain.repositorios.RepositorioUsuario;
import domain.entidades.usuario.Usuario;
import domain.repositorios.factories.FactoryRepositorio;
import domain.repositorios.factories.FactoryRepositorioUsuarios;
import net.glxn.qrgen.core.scheme.EMail;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioController {

    public static UsuarioController instancia;

    private RepositorioUsuario repositorioUsuario = FactoryRepositorioUsuarios.get();
    private Repositorio<Usuario> repositorio;


    public UsuarioController() {
        this.repositorio = FactoryRepositorio.get(Usuario.class);
    }

    public static UsuarioController getInstancia() {
        if(UsuarioController.instancia == null)
            instancia = new UsuarioController();
        return instancia;
    }





    public ModelAndView registrarUsuario(Request request, Response response) throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        String tipo0 = request.queryParams("nombre");

        if (tipo0 == null) {

            return new ModelAndView(parametros, "/registrarUsuario.hbs");

        } else {
            Usuario usuario = new Usuario();
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

            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setUsuario(nombreUsuario);

            if (!contrasenia1.equals(contrasenia2)) {
                throw new Exception("Las contraseñas no coinciden.");
            }
            if (this.repositorioUsuario.existeUsuario(nombreUsuario)) {
                return new ModelAndView(parametros, "/usuarioExistente.hbs");
            }
            if (usuario.validarContrasenia(contrasenia1)) {
                usuario.setContrasenia(contrasenia1);
            } else {
                return new ModelAndView(parametros, "/seguridadContrasenia.hbs");
            }

            usuario.setEmail(email);
            usuario.setDomicilio(domicilio);


             usuario.setFechaDeNacimiento(new Date(4,8,1875));


            switch (tipoDocumentacion) {
                case "DNI":
                    usuario.setTipoDeDocumento(TipoDeDocumento.DNI);
                    break;
                case "LIBRETA_CIVICA":
                    usuario.setTipoDeDocumento(TipoDeDocumento.LIBRETA_CIVICA);
                    break;
                case "LIBRETA_ENROLAMIENTO":
                    usuario.setTipoDeDocumento(TipoDeDocumento.LIBRETA_ENROLAMIENTO);
                    break;
                default:
                    usuario.setTipoDeDocumento(TipoDeDocumento.CEDULA);
                    break;
            }

            usuario.setDocumento(documento);

            Contacto contactoUsuario = new Contacto();
            String nombreContacto = request.queryParams("nombreContacto");
            String apellidoContacto = request.queryParams("apellidoContacto");
            String telefonoContacto = request.queryParams("telefono");
            String emailContacto = request.queryParams("emailContacto");
            String formaDeNotificacion1 = request.queryParams("formasDeNotificacion1");
            String formaDeNotificacion2 = request.queryParams("formasDeNotificacion2");
            String formaDeNotificacion3 = request.queryParams("formasDeNotificacion3");

            contactoUsuario.setNombre(nombreContacto);
            contactoUsuario.setApellido(apellidoContacto);
            contactoUsuario.setTelefono(telefonoContacto);
            contactoUsuario.setMail(emailContacto);

            if (formaDeNotificacion1 != null && formaDeNotificacion1.equals("Whatsapp")) {
                EstrategiaDeNotificacion watsapp = new Whatsapp();
                contactoUsuario.agregarFormaDeNotificacion(watsapp);
            }

            if (formaDeNotificacion2 != null && formaDeNotificacion2.equals("Email")) {
                EstrategiaDeNotificacion mail = new Mail();
                contactoUsuario.agregarFormaDeNotificacion(mail);
            }

            if (formaDeNotificacion3 != null && formaDeNotificacion3.equals("SMS")) {
                EstrategiaDeNotificacion sms = new SMS();
                contactoUsuario.agregarFormaDeNotificacion(sms);
            }


            usuario.agregarContacto(contactoUsuario);


            if(noEstaEnElRepo(usuario, repositorio.buscarTodos()) == 12) {
                repositorio.agregar(usuario);}

            return new ModelAndView(parametros, "/registroExitosoUsuario.hbs");
        }
    }

    private Integer noEstaEnElRepo(Usuario usuario, List<Usuario> repositorio1) {

        for (Usuario usuarioCualquiera : repositorio1) {

            if (usuarioCualquiera.getDocumento().equals(usuario.getDocumento())) {

                usuarioCualquiera.setNombre(usuario.getNombre());
                usuarioCualquiera.setApellido(usuario.getApellido());
                usuarioCualquiera.setDomicilio(usuario.getDomicilio());
                usuarioCualquiera.setContrasenia(usuario.getContrasenia());
                usuarioCualquiera.setEmail(usuario.getEmail());
                //usuarioCualquiera.setFechaDeNacimiento(usuario.getFechaDeNacimiento());
                usuarioCualquiera.setTipoDeDocumento(usuario.getTipoDeDocumento());
                usuarioCualquiera.setUsuario(usuario.getUsuario());



                repositorio.modificar(usuarioCualquiera);


                return 13;

            }
        }
        return 12;
    }

    public ModelAndView registrarSINUsuario(Request request, Response response) throws Exception {
            Map<String, Object> parametros = new HashMap<>();
            String tipo0 = request.queryParams("documento");


            if(tipo0 == null) {
                return new ModelAndView(parametros, "/registrarMascotaSinUsuario.hbs");

            } else {
                Usuario usuario = new Usuario();
                String documento = request.queryParams("documento");
                String numeroTramite = request.queryParams("tramite");
                usuario.setDocumento(documento);
                usuario.setnTramite(numeroTramite);
                usuario.setTipoDeDocumento(TipoDeDocumento.DNI);
                usuario.setDomicilio("nulo");
                usuario.setNombre("nulo");
                usuario.setApellido("nulo");
                usuario.setUsuario("nulo");
                usuario.setContrasenia("nulo");
                usuario.setEmail("nulo");


                if(this.repositorioUsuario.existeDNIyNumeroDeTramiteSinUsuario(documento,numeroTramite)){
                    request.session().attribute("usuarioLogeadoDoc",documento);
                    request.session().attribute("usuarioLogeadoNumTramite",numeroTramite);
                } else{
                    repositorio.agregar(usuario);
                    request.session().attribute("usuarioLogeadoDoc",documento);
                    request.session().attribute("usuarioLogeadoNumTramite",numeroTramite);
                }
                response.redirect("/formularioEsMiMascotaSinUsuario");
                return null;
            }

    }
}

