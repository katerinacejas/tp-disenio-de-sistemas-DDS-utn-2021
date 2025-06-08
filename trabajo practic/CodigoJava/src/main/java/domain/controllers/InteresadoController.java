package domain.controllers;

import domain.entidades.mascota.*;
import domain.entidades.organizacion.Organizacion;
import domain.entidades.publicacion.PreguntaDarEnAdopcion;
import domain.entidades.publicacion.PreguntaObligatoria;
import domain.entidades.publicacion.PublicacionQuererAdoptarConComodidades;
import domain.entidades.usuario.Contacto;
import domain.entidades.usuario.SolicitudesAdministrador;
import domain.entidades.usuario.Usuario;
import domain.entidades.usuario.formasNotificacion.EstrategiaDeNotificacion;
import domain.entidades.usuario.formasNotificacion.Mail;
import domain.entidades.usuario.formasNotificacion.SMS;
import domain.entidades.usuario.formasNotificacion.Whatsapp;
import domain.repositorios.Repositorio;
import domain.repositorios.RepositorioInteresados;
import  domain.entidades.usuario.Interesado;
import domain.repositorios.RepositorioUsuario;
import domain.repositorios.factories.FactoryRepositorio;
import domain.repositorios.factories.FactoryRepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InteresadoController {
    private static InteresadoController instancia;

    private Repositorio<Interesado> interesadoRepositorio;
    private RepositorioUsuario repositorioUsuario = FactoryRepositorioUsuarios.get();
    private Repositorio<Usuario> repoUsuario;
    private Repositorio<PreguntaObligatoria> repositorioPreguntas;

    public Repositorio<PublicacionQuererAdoptarConComodidades> getRepositorioPublicacionIntencionAdoptar() {
        return repositorioPublicacionIntencionAdoptar;
    }

    private Repositorio<PublicacionQuererAdoptarConComodidades> repositorioPublicacionIntencionAdoptar;

    public InteresadoController() {
        this.interesadoRepositorio = FactoryRepositorio.get(Interesado.class);
        this.repoUsuario = FactoryRepositorio.get(Usuario.class);
        this.repositorioPreguntas = FactoryRepositorio.get(PreguntaObligatoria.class);
        this.repositorioPublicacionIntencionAdoptar =  FactoryRepositorio.get(PublicacionQuererAdoptarConComodidades.class);
    }


    public static InteresadoController getInstancia() {
        if (InteresadoController.instancia == null) {
            instancia = new InteresadoController();
        }
        return instancia;
    }


    public ModelAndView quererAdoptarConComodidades(Request request, Response response) throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        Interesado interesado = new Interesado();

        String tipo0 = request.queryParams("mascota");


        if(tipo0 == null) {
            return new ModelAndView(parametros, "/quererAdoptarConComodidades.hbs");

        } else {
            String sexo = request.queryParams("sexo");
            String mascota = request.queryParams("mascota"); // tipo perro o gato
            String tamanio = request.queryParams("tamanio");
            /*String tienePatio = request.queryParams("tienePatio");
            String tieneOtrasMascotas = request.queryParams("tieneOtrasMascotas");
            String tieneTiempo = request.queryParams("tieneTiempo");*/


            if(mascota.equals("Gato")) {
                interesado.setTipo(TipoDeMascota.GATO);
                interesado.setEsPerro(false);
            } else if(mascota.equals("Perro")) {
                interesado.setTipo(TipoDeMascota.PERRO);
                interesado.setEsPerro(true);
            }

            /*if(tienePatio.equals("Si")) {
                interesado.setTienePatio(true);
            } else if(tienePatio.equals("No")) {
                interesado.setTienePatio(false);
            }

            if(tieneOtrasMascotas.equals("Si")) {
                interesado.setTieneOtrasMascotas(true);
            } else if(tieneOtrasMascotas.equals("No")) {
                interesado.setTieneOtrasMascotas(false);
            }

            if(tieneTiempo.equals("Si")) {
                interesado.setTieneTiempo(true);
            } else if(tieneTiempo.equals("No")) {
                interesado.setTieneTiempo(false);
            }*/

            if(sexo.equals("Macho")) {
                interesado.setSexo(TipoSexo.MASCULINO);
            } else if(sexo.equals("Hembra")) {
                interesado.setSexo(TipoSexo.FEMENINO);
            }

            if(tamanio.equals("Chica")) {
                interesado.setTamanio(TamanioMascota.CHICA);
            } else if(tamanio.equals("Mediana")) {
                interesado.setTamanio(TamanioMascota.MEDIANA);
            } else if(tamanio.equals("Grande")) {
                interesado.setTamanio(TamanioMascota.GRANDE);
            }

            String usuario = request.session().attribute("usuarioLogeado");

            int idUsuario = this.repositorioUsuario.usuarioID(usuario);

            Usuario usuarioGeneraInteres = this.repoUsuario.buscar(idUsuario);
            interesado.setUsuario(usuarioGeneraInteres.getUsuario());
            interesado.setContrasenia(usuarioGeneraInteres.getContrasenia());
            interesado.setEmail(usuarioGeneraInteres.getEmail());

            Contacto contactoInteresado = new Contacto();
            EstrategiaDeNotificacion wpp = new Whatsapp();
            contactoInteresado.agregarFormaDeNotificacion(wpp);
            EstrategiaDeNotificacion sms = new SMS();
            contactoInteresado.agregarFormaDeNotificacion(sms);
            EstrategiaDeNotificacion mail = new Mail();
            contactoInteresado.agregarFormaDeNotificacion(mail);
            contactoInteresado.setNombre("Belen");
            contactoInteresado.setApellido("Seoane");
            contactoInteresado.setMail("katerinacejas3@gmail.com");
            contactoInteresado.setTelefono("+5491162805877");

            List<Contacto> listaContactoInteresado = new ArrayList<>();
            listaContactoInteresado.add(contactoInteresado);

            interesado.setListaContacto(listaContactoInteresado);
            interesado.setNombre(usuarioGeneraInteres.getNombre());
            interesado.setApellido(usuarioGeneraInteres.getApellido());
            interesado.setDomicilio(usuarioGeneraInteres.getDomicilio());
            interesado.setFechaDeNacimiento(usuarioGeneraInteres.getFechaDeNacimiento());
            interesado.setnTramite(usuarioGeneraInteres.getnTramite());
            interesado.setDocumento(usuarioGeneraInteres.getDocumento());
            interesado.setTipoDeDocumento(usuarioGeneraInteres.getTipoDeDocumento());

            interesadoRepositorio.agregar(interesado);

            PublicacionQuererAdoptarConComodidades publicacion = new PublicacionQuererAdoptarConComodidades();
            publicacion.setAprobada(false);
            publicacion.setFormulario(interesado);

            repositorioPublicacionIntencionAdoptar.agregar(publicacion);

            return new ModelAndView(parametros, "adoptarConComodidadesExitoso.hbs");
        }

    }


}