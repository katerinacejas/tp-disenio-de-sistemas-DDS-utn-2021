package domain.controllers;

import com.google.zxing.WriterException;
import domain.entidades.apiRefugios.entities.*;
import domain.entidades.mascota.*;
import domain.entidades.publicacion.PreguntaObligatoria;
import domain.entidades.publicacion.PublicacionDarEnAdopcion;
import domain.entidades.publicacion.PublicacionMascotaPerdida;
import domain.entidades.usuario.*;

import domain.entidades.usuario.formasNotificacion.EstrategiaDeNotificacion;
import domain.entidades.usuario.formasNotificacion.Mail;
import domain.entidades.usuario.formasNotificacion.SMS;
import domain.entidades.usuario.formasNotificacion.Whatsapp;
import domain.repositorios.*;
import domain.repositorios.factories.FactoryRepositorio;
import domain.repositorios.factories.FactoryRepositorioMascota;
import domain.repositorios.factories.FactoryRepositorioPersona;
import domain.repositorios.factories.FactoryRepositorioUsuarios;
import org.apache.commons.io.FileUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;


import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class MascotasController {
    // public RepositorioMascota repositorioMascota;
    public static MascotasController instancia;
    public GeneracionQR generadorQR;
    //public ConfiguracionFoto configuradorFoto;

    private Repositorio<CaracteristicaDeMascotas> caracteristicasRepositorio;
    private Repositorio<PublicacionMascotaPerdida> publicacionMascotaPerdidaRepositorio;

    private RepositorioPersona repositorioPersona = FactoryRepositorioPersona.get();
    private Repositorio<Persona> personaRepositorio;

    private RepositorioUsuario repositorioUsuario = FactoryRepositorioUsuarios.get();
    private Repositorio<Usuario> repoUsuario;

    private RepositorioMascota repositorioMascota = FactoryRepositorioMascota.get();
    private Repositorio<Mascota> repositorio;

    private Repositorio<PublicacionDarEnAdopcion> publicacionDarEnAdopcionRepositorio;

    public RepositorioHogares repositorioHogares = new RepositorioHogares();

    private Repositorio<PreguntaObligatoria> repositorioPreguntas;


    public static MascotasController getInstancia() {
        if(MascotasController.instancia == null)
            instancia = new MascotasController();
        return instancia;
    }

    public MascotasController() {
        this.generadorQR = new GeneracionQR();
        //this.configuradorFoto = new ConfiguracionFoto();
        this.repositorio = FactoryRepositorio.get(Mascota.class);
        this.caracteristicasRepositorio = FactoryRepositorio.get(CaracteristicaDeMascotas.class);
        this.publicacionMascotaPerdidaRepositorio = FactoryRepositorio.get(PublicacionMascotaPerdida.class);
        this.personaRepositorio = FactoryRepositorio.get(Persona.class);
        this.repoUsuario = FactoryRepositorio.get(Usuario.class);
        this.publicacionDarEnAdopcionRepositorio = FactoryRepositorio.get(PublicacionDarEnAdopcion.class);
        this.repositorioPreguntas = FactoryRepositorio.get(PreguntaObligatoria.class);

    }

    public ModelAndView misMascotas(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        //parametros.put("usuario",request.session().attribute("usuarioLogeado"));

        String nombreUsuario = request.session().attribute("usuarioLogeado");

        int usuarioId = this.repositorioUsuario.usuarioID(nombreUsuario);
        if(usuarioId == 0){
            return new ModelAndView(parametros,"/");
        }
        Usuario usuario = this.repoUsuario.buscar(usuarioId);

        List<Mascota> mascotas = this.repositorio.buscarTodos();

        List<Mascota> misMascotas = this.repositorioMascota.esMiMascota(mascotas, usuario);

        parametros.put("mascota", misMascotas);

        return new ModelAndView(parametros,"/misMascotasUsuario.hbs");
    }

    public ModelAndView miMascota(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();

        Mascota mascota = this.repositorio.buscar(new Integer(request.params("id")));

        parametros.put("mascota", mascota);

        String adopcion = request.queryParams("adopcion");
        String estado = request.queryParams("estado");

        if(adopcion != null && adopcion.equals("Dar En Adopción")) {
            mascota.setQuererDarlaEnAdopcion(true);

            repositorio.modificar(mascota);

            PublicacionDarEnAdopcion publicacionDarEnAdopcion = new PublicacionDarEnAdopcion();
            publicacionDarEnAdopcion.setMascota(mascota);
            System.out.print("1");
            this.publicacionDarEnAdopcionRepositorio.agregar(publicacionDarEnAdopcion);

            response.redirect("/formularioQuererDarEnAdopcionMascota");
            //return new ModelAndView(parametros, "/formularioQuererDarEnAdopcionMascota.hbs");

        } else if(estado != null && estado.equals("Informar Perdida")) {
            mascota.setPerdida(true);
            repositorio.modificar(mascota);
            PublicacionMascotaPerdida publicacionMascotaPerdida = new PublicacionMascotaPerdida();
            publicacionMascotaPerdida.setDuenioLaBusca(true);
            publicacionMascotaPerdida.setMascota(mascota);
            this.publicacionMascotaPerdidaRepositorio.agregar(publicacionMascotaPerdida);

        }


        return new ModelAndView(parametros,"/verMiMascota.hbs");
    }

    public ModelAndView formularioEsMiMascota(Request request, Response response) throws Exception {
        Map<String, Object> parametros = new HashMap<>();

        /*usuario mascota*/
        String usuario = request.session().attribute("usuarioLogeado");

        int idUsuario = this.repositorioUsuario.usuarioID(usuario);

        Usuario duenioMascota = this.repoUsuario.buscar(idUsuario);

        List<CaracteristicaDeMascotas> caracteristicas = this.caracteristicasRepositorio.buscarTodos();
        parametros.put("caracteristicademascotas", caracteristicas);

        String tipo0 = request.queryParams("mascota");

        if(tipo0 == null) {

            return new ModelAndView(parametros, "/formularioEsMiMascota.hbs");

        } else {
            Mascota mascota = new Mascota();
            String tipo = request.queryParams("mascota");
            String nombre = request.queryParams("nombre");
            String apodo = request.queryParams("apodo");
            String edad = request.queryParams("edad");
            String sexo = request.queryParams("sexo");
            String descripcion = request.queryParams("descripcion");
            String aclaracion = request.queryParams("aclaracion");
            String foto = request.queryParams("fileupload");
            String tamanio = request.queryParams("tamanio");

            if (tipo.equals("Gato")) {
                mascota.setTipo(TipoDeMascota.GATO);
            } else if (tipo.equals("Perro")) {
                mascota.setTipo(TipoDeMascota.PERRO);
            }

            mascota.setNombreMascota(nombre);
            mascota.setApodo(apodo);
            mascota.setEdad(Integer.parseInt(edad));
            mascota.setDescripcionFisica(descripcion);

            mascota.setDuenio(duenioMascota);
            mascota.setRuta(" ");

            if(sexo.equals("Macho")) {
                mascota.setSexo(TipoSexo.MASCULINO);
            } else if(sexo.equals("Hembra")){
                mascota.setSexo(TipoSexo.FEMENINO);
            }

            switch (tamanio) {
                case "Grande":
                    mascota.setTamanio(TamanioMascota.GRANDE);
                    break;
                case "Mediana":
                    mascota.setTamanio(TamanioMascota.MEDIANA);
                    break;
                case "Chica":
                    mascota.setTamanio(TamanioMascota.CHICA);
                    break;
            }
            mascota.setPerdida(false);

            //para la foto
            switch (mascota.getNombreMascota()){
                case "Papa Frita":
                    mascota.setRuta("../img/fotosMascotas/papafrita.png");
                    break;
                case "Brocoli":
                    mascota.setRuta("../img/fotosMascotas/brocoli.png");
                    break;
                case "Huevo Frito":
                    mascota.setRuta("../img/fotosMascotas/huevofrito.png");
                    break;
                case "Milanesa":
                    mascota.setRuta("../img/fotosMascotas/milanesa.png");
                    break;
            }

            repositorio.agregar(mascota);
            TimerTask task = new TimerTask() {
                public void run() {
                    System.out.println("Task performed on: " + new Date() + "n" +
                            "Thread's name: " + Thread.currentThread().getName());
                }
            };
            Timer timer = new Timer("Timer");

            long delay = 100;
            timer.schedule(task, delay);
            idMascota = mascota.getId();

            //GeneracionQR generacionQR = new GeneracionQR();
            //generacionQR.generarQR(idMascota, "Ingrese el ID en el link: (definir link) ");

            response.redirect("/mascotaCargadaExitosamente");
        }
        return new ModelAndView(parametros, "/formularioEsMiMascota.hbs");
    }
    private Integer idMascota;

    public ModelAndView subirFotoMascota(Request request, Response response) throws IOException, ServletException, InterruptedException {
        Map<String, Object> parametros = new HashMap<>();
        File uploadDir = new File("imagenUsuario");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist
        String tempFileAtributo=".jpg";
        Path tempFile = Files.createTempFile(uploadDir.toPath(), "", tempFileAtributo);


        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

        try (InputStream input = request.raw().getPart("uploaded_file").getInputStream()) { // getPart needs to use same "name" as input field in form
            Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }

        logInfo(request, tempFile);
        Path path = tempFile.getFileName();
        String pathString = path.toString();
        Mascota mascota = repositorioMascota.buscar(idMascota);
        mascota.setRuta(pathString);

        parametros.put("pathimagen",path);
        System.out.println(parametros.get("pathimagen"));
        repositorio.modificar(mascota);

        if(request.session().attribute("usuarioLogeado") == null){ // TODO ver tal vez da lugar a exceptions
            return new ModelAndView(parametros, "paginaPrincipalSinUsuario.hbs");

        } else {
                return new ModelAndView(parametros, "mascotaCargadaExitosamente.hbs");

        }
    }

    // methods used for logging
    private static void logInfo(Request req, Path tempFile) throws IOException, ServletException {
        System.out.println("Uploaded file '" + getFileName(req.raw().getPart("uploaded_file")) + "' saved as '" + tempFile.toAbsolutePath() + "'");
    }

    private static String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public ModelAndView mostrarQR(Request request, Response response) throws Exception {
        Map<String, Object> parametros = new HashMap<>();

        String rutaFinal = "../img/QR" + (idMascota) + ".jpg";

        //System.out.println(rutaFinal);
        //parametros.put("ruta", rutaFinal);

        String url = "ID mascota: " + idMascota + " link: https://petpaloficial2.herokuapp.com/formularioEstaPerdidaSinUsuario" ;
        GeneracionQR generacionQR = new GeneracionQR();
        String imagen = generacionQR.getQrInfo(url);

        System.out.println(imagen);
        parametros.put("imagen",imagen);

        if(request.session().attribute("usuarioLogeado") == null){ // TODO ver tal vez da lugar a exceptions
            String ruta = "../paginaPrincipalSinUsuario";
            parametros.put("ruta", ruta);
            return new ModelAndView(parametros, "mascotaCargadaExitosamente.hbs");
        } else {
            String ruta = "../index";
            parametros.put("ruta", ruta);
            return new ModelAndView(parametros, "mascotaCargadaExitosamente.hbs");
        }
    }

    public ModelAndView formularioEstaPerdida(Request request, Response response) throws Exception {
        Map<String, Object> parametros = new HashMap<>();

        String tipo0 = request.queryParams("mascota");

        if(tipo0 == null) {
            return new ModelAndView(parametros, "/formularioEstaPerdida.hbs");
        } else {

            PublicacionMascotaPerdida publicacionMascotaPerdida = new PublicacionMascotaPerdida();
            Rescatista rescatista = new Rescatista();

            String tipo = request.queryParams("mascota");
            String descripcion = request.queryParams("descripcion");;
            //String ruta = request.queryParams("fileupload");
            String lat = request.queryParams("latitud");
            String longi = request.queryParams("longitud");
            String lugar = request.queryParams("desLugar");

            if(tipo.equals("Gato")) {
                rescatista.setTipoDeMascota(TipoDeMascota.GATO);
            } else if(tipo.equals("Perro")) {
                rescatista.setTipoDeMascota(TipoDeMascota.PERRO);
            } else {
                throw new Exception("Tipo de mascota no válido");
            }

            rescatista.setDescripcionMascota(descripcion);
            rescatista.setLatitud(lat);
            rescatista.setLongitud(longi);
            rescatista.setLugarDondeEncontroAMascota(lugar);

            String usuario = request.session().attribute("usuarioLogeado");
            int idUsuario = this.repositorioUsuario.usuarioID(usuario);
            Usuario rescatistaInfo = this.repoUsuario.buscar(idUsuario);
            Contacto contacto = new Contacto();
            contacto.setNombre("Belen");
            contacto.setApellido("Seoane");
            contacto.setTelefono("+5491162805877");
            //contacto.setMail("katerinacejas3@gmail.com");
            List<EstrategiaDeNotificacion> estrategias = new ArrayList<>();
            EstrategiaDeNotificacion wpp = new Whatsapp();
            EstrategiaDeNotificacion sms = new SMS();
            //EstrategiaDeNotificacion mail = new Mail();
            estrategias.add(wpp);
            //estrategias.add(mail);
            estrategias.add(sms);
            contacto.setFormasDeNotificacion(estrategias);
            rescatista.setContacto(contacto);
            //rescatista.setContacto(rescatistaInfo.getListaContacto().stream().findFirst().get()); // SE SETEA EL PRIMERO DE LOS CONTACTOS
            rescatista.setNombre(rescatistaInfo.getNombre());
            rescatista.setApellido(rescatistaInfo.getApellido());

            //publicacionMascotaPerdida.setPathFotosEnCarrusel(ruta);
            publicacionMascotaPerdida.setPathFotosEnCarrusel("../img/fotosMascotas/perdidaConUsuario.png");
            publicacionMascotaPerdida.setFormulario(rescatista);
            publicacionMascotaPerdida.setAprobada(false);
            publicacionMascotaPerdida.setDuenioLaBusca(false);
            publicacionMascotaPerdida.setEncontradaPorRescatista(true);
            publicacionMascotaPerdidaRepositorio.agregar(publicacionMascotaPerdida);

            String tamanio = request.queryParams("tamanio");

            Mascota mascota = new Mascota();
            TamanioMascota tamanioMascota = mascota.obtenerTamanio(tamanio);
            TipoDeMascota tipoDeMascota = mascota.obtenerTipo(tipo);

            Filter filterRadio = new FilterRadio(Integer.parseInt(request.queryParams("radio")), (int) Math.round(new Double((longi))), (int) Math.round(new Double((lat))));
            Filter filterTamanio = new FilterTamanio(tamanioMascota);
            Filter filterTipo = new FilterTipo(tipoDeMascota);

            List<Filter> listaDeFilter = new ArrayList<>();
            listaDeFilter.add(filterRadio);
            listaDeFilter.add(filterTamanio);
            listaDeFilter.add(filterTipo);

            HogaresController hogaresController = new HogaresController();
            List<Hogar> hogaresFiltrados = hogaresController.retornarHogaresFiltrados(listaDeFilter);

            parametros.put("hogar", hogaresFiltrados);

            String idMascota = request.queryParams("id");
            if(idMascota.equals("")){
               idMascota = "0";
            }
            if(Integer.parseInt(idMascota) > 0) {
                Mascota encontrada = repositorio.buscar(Integer.parseInt(idMascota));
                List<Contacto> contactos = encontrada.getDuenio().getListaContacto();

                for (Contacto c : contactos) {
                    c.notificar("Le informamos que su mascota "+encontrada.getNombreMascota() + " ha sido encontrada. Comuniquese con Belen Seoane al numero +5491162805877 y/o mail katerinacejas3@gmail.com");
                }
            }
            return new ModelAndView(parametros,"/hogaresTransito.hbs");
        }
    }


    public ModelAndView formularioEstaPerdidaQR(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"/formularioEstaPerdidaQR.hbs");
    }

    public ModelAndView misMascotasSU(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();

        String doc = request.session().attribute("usuarioLogeadoDoc");
        String numeroTramite = request.session().attribute("usuarioLogeadoNumTramite");

        if (doc != null || numeroTramite!= null ){
            //parametros.put("usuario",request.session().attribute("usuarioLogeado"));
            int idUsuario = this.repositorioUsuario.usuarioIDporDocumento(doc, numeroTramite);
            if(idUsuario == -1){
                return new ModelAndView(parametros, "/registrarMascotaSinUsuario.hbs");//
            }else {

                Usuario usuarioSinRegistrar = this.repoUsuario.buscar(idUsuario);

                List<Mascota> mascotas = this.repositorio.buscarTodos();

                List<Mascota> misMascotas = this.repositorioMascota.esMiMascota(mascotas, usuarioSinRegistrar);

                parametros.put("mascota", misMascotas);
                return new ModelAndView(parametros, "/verMascotasSinUsuario.hbs");//
            }
        }else{
            String nDoc = request.queryParams("numeroDoc");
            String nTramite = request.queryParams("nTramite");
            if(nDoc == null || nTramite == null ){
                return new ModelAndView(parametros,"/misMascotasSinUsuario.hbs");//
            }else {
                int idUsuario1 = this.repositorioUsuario.usuarioIDporDocumento(nDoc, nTramite);

                if(idUsuario1 == -1){
                    return new ModelAndView(parametros,"/misMascotasSinUsuario.hbs");
                }else {

                    Usuario usuarioSinRegistrar = this.repoUsuario.buscar(idUsuario1);

                    List<Mascota> mascotas = this.repositorio.buscarTodos();

                    List<Mascota> misMascotas = this.repositorioMascota.esMiMascota(mascotas, usuarioSinRegistrar);

                    parametros.put("mascota", misMascotas);
                    return new ModelAndView(parametros, "/verMascotasSinUsuario.hbs");
                }
            }
        }
    }

    public ModelAndView seccionRegistrarMascotas(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"/seccionRegistrarMascotas.hbs");//
    }

    public ModelAndView seccionRegistrarMascotaSU(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"/seccionRegistrarMascotaSinUsuario.hbs");
    }

    public ModelAndView estaPerdidaaSU(Request request, Response response) throws IOException {
        Map<String, Object> parametros = new HashMap<>();

        String nombre = request.queryParams("nombre");

        if(nombre != null) {

            String apellido = request.queryParams("apellido");
            String nacimiento = request.queryParams("nacimiento");
            String tipoDoc = request.queryParams("tipoDocumento");
            String nDoc = request.queryParams("numeroDoc");
            String nTramite = request.queryParams("nTramite");
           // String email = request.queryParams("email");
            String telefono = request.queryParams("telefono");
            String formaNotif = request.queryParams("formaDeNoticacion");// TODO COMO SE LE SETEA UN TIPO DE NOTIFICACION ?
            String tipo = request.queryParams("mascota");
            //String ruta = request.queryParams("fileupload");
            String descripcion = request.queryParams("descripcion");
            String latitud = request.queryParams("latitud");
            String longitud = request.queryParams("longitud");
            String lugar = request.queryParams("desLugar");

            Contacto contacto = new Contacto();
            contacto.setNombre("Belen");
            contacto.setApellido("Seoane");
            contacto.setTelefono("+5491162805877");
            //contacto.setMail("katerinacejas3@gmail.com");
            List<EstrategiaDeNotificacion> estrategias = new ArrayList<>();
            EstrategiaDeNotificacion wpp = new Whatsapp();
            EstrategiaDeNotificacion sms = new SMS();
            //EstrategiaDeNotificacion mail = new Mail();
            estrategias.add(wpp);
           // estrategias.add(mail);
            estrategias.add(sms);
            contacto.setFormasDeNotificacion(estrategias);


            Rescatista rescatista = new Rescatista();

            rescatista.setNombre(nombre);
            rescatista.setApellido(apellido);
            rescatista.setDescripcionMascota(descripcion);
            rescatista.setContacto(contacto);
            rescatista.setnTramite(nTramite);
            rescatista.setDocumento(nDoc);
            rescatista.setLatitud(latitud);
            rescatista.setLongitud(longitud);
            rescatista.setLugarDondeEncontroAMascota(lugar);

            if(tipo.equals("Gato")) {
                rescatista.setTipoDeMascota(TipoDeMascota.GATO);
            } else if(tipo.equals("Perro")) {
                rescatista.setTipoDeMascota(TipoDeMascota.PERRO);
            }

            switch (tipoDoc) {
                case "DNI":
                    rescatista.setTipoDeDocumento(TipoDeDocumento.DNI);
                    break;
                case "Libreta Cívica":
                    rescatista.setTipoDeDocumento(TipoDeDocumento.LIBRETA_CIVICA);
                    break;
                case "Cédula":
                    rescatista.setTipoDeDocumento(TipoDeDocumento.CEDULA);
                    break;
                case "Libreta de enloramiento":
                    rescatista.setTipoDeDocumento(TipoDeDocumento.LIBRETA_ENROLAMIENTO);
                    break;
            }

            PublicacionMascotaPerdida publicacionMascotaPerdida = new PublicacionMascotaPerdida();

            //publicacionMascotaPerdida.setPathFotosEnCarrusel(ruta);
            publicacionMascotaPerdida.setPathFotosEnCarrusel("../img/fotosMascotas/perdidaSinUsuario.png");
            publicacionMascotaPerdida.setFormulario(rescatista);
            publicacionMascotaPerdidaRepositorio.agregar(publicacionMascotaPerdida);
            publicacionMascotaPerdida.setAprobada(false);
            publicacionMascotaPerdida.setDuenioLaBusca(false);
            publicacionMascotaPerdida.setEncontradaPorRescatista(true);

            String tamanio = request.queryParams("tamanio");

            Mascota mascota = new Mascota();
            TamanioMascota tamanioMascota = mascota.obtenerTamanio(tamanio);
            TipoDeMascota tipoDeMascota = mascota.obtenerTipo(tipo);

            Filter filterRadio = new FilterRadio(Integer.parseInt(request.queryParams("radio")), (int) Math.round(new Double((longitud))), (int) Math.round(new Double((latitud))));
            Filter filterTamanio = new FilterTamanio(tamanioMascota);
            Filter filterTipo = new FilterTipo(tipoDeMascota);
            List<Filter> listaDeFilter = new ArrayList<>();
            listaDeFilter.add(filterRadio);
            listaDeFilter.add(filterTamanio);
            listaDeFilter.add(filterTipo);
            HogaresController hogaresController = new HogaresController();
            List<Hogar> hogaresFiltrados = hogaresController.retornarHogaresFiltrados(listaDeFilter);
            parametros.put("hogar", hogaresFiltrados);
            String idMascota = request.queryParams("id");
            if(idMascota.equals("")){
                idMascota = "0";
            }
            if(Integer.parseInt(idMascota) > 0) {
                Mascota encontrada = repositorio.buscar(Integer.parseInt(idMascota));
                List<Contacto> contactos = encontrada.getDuenio().getListaContacto();
                for (Contacto c : contactos) {
                    c.notificar("Le informamos que su mascota " + encontrada.getNombreMascota() + " ha sido encontrada. Comuniquese con Belen Seoane al numero +5491162805877 y/o mail katerinacejas3@gmail.com");
                }
            }
            return new ModelAndView(parametros,"/hogaresTransitoSinUsuario.hbs");
        }
        return new ModelAndView(parametros,"/formularioEstaPerdidaSinUsuario.hbs");
    }

    public ModelAndView registrarMascotaSU(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();

        String tipo0 = request.queryParams("tipo");

        List<CaracteristicaDeMascotas> caracteristicas = this.caracteristicasRepositorio.buscarTodos();
        parametros.put("caracteristicademascotas", caracteristicas);

        if(tipo0 == null) {
            return new ModelAndView(parametros, "/formularioEsMiMascotaSinUsuario.hbs");

        }else{

        String doc = request.session().attribute("usuarioLogeadoDoc");
        String numeroTramite = request.session().attribute("usuarioLogeadoNumTramite");

        int idUsuario = this.repositorioUsuario.usuarioIDporDocumento(doc,numeroTramite);
        Usuario usuarioSinRegistrar = this.repoUsuario.buscar(idUsuario);

            String tipo = request.queryParams("tipo");
            String nombre = request.queryParams("nombre");
            String apodo = request.queryParams("apodo");
            String edad = request.queryParams("edad");
            String sexo = request.queryParams("sexo");
            String descripcion = request.queryParams("descripcion");
            String tamanio = request.queryParams("tamanio");

            Mascota mascota = new Mascota();

            mascota.setNombreMascota(nombre);
            mascota.setApodo(apodo);
            mascota.setDescripcionFisica(descripcion);
            mascota.setRuta(" ");
            mascota.setDuenio(usuarioSinRegistrar);

            if (tipo.equals("Gato")) {
                mascota.setTipo(TipoDeMascota.GATO);
            } else if (tipo.equals("Perro")) {
                mascota.setTipo(TipoDeMascota.PERRO);
            }

            mascota.setEdad(Integer.parseInt(edad));

            if (sexo.equals("Macho")) {
                mascota.setSexo(TipoSexo.MASCULINO);
            } else if (sexo.equals("Hembra")) {
                mascota.setSexo(TipoSexo.FEMENINO);
            }

            if (tamanio.equals("Grande")) {
                mascota.setTamanio(TamanioMascota.GRANDE);
            } else if (tamanio.equals("Mediana")) {
                mascota.setTamanio(TamanioMascota.MEDIANA);
            }
            if (tamanio.equals("Chica")) {
                mascota.setTamanio(TamanioMascota.CHICA);
            }

            //para la foto
            switch (mascota.getNombreMascota()){
                case "Pure":
                    mascota.setRuta("../img/fotosMascotas/pure.png");
                    break;
                case "Fideo":
                    mascota.setRuta("../img/fotosMascotas/fideo.png");
                    break;
                case "Galletita":
                    mascota.setRuta("../img/fotosMascotas/galletita.png");
                    break;
                case "Tomate":
                    mascota.setRuta("../img/fotosMascotas/tomate.png");
                    break;
            }

            mascota.setPerdida(false);
            repositorio.agregar(mascota);
            TimerTask task = new TimerTask() {
                public void run() {
                    System.out.println("Task performed on: " + new Date() + "n" +
                            "Thread's name: " + Thread.currentThread().getName());
                }
            };
            Timer timer = new Timer("Timer");

            long delay = 100;
            timer.schedule(task, delay);
            idMascota = mascota.getId();

           // return new ModelAndView(parametros, "/subirFotoMascotaSU.hbs");
            response.redirect("mascotaCargadaExitosamente");
        }
        return new ModelAndView(parametros, "/formularioEsMiMascotaSinUsuario.hbs");
    }

    public ModelAndView adoptar(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"/adoptarUnaMascota.hbs");
    }

    public ModelAndView mascotasPerdidas(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();

        List<PublicacionMascotaPerdida> publicacionMascotaPerdidas = this.publicacionMascotaPerdidaRepositorio.buscarTodos();

        parametros.put("publicacion",publicacionMascotaPerdidas);
        return new ModelAndView(parametros,"/mascotasPerdidas.hbs");
    }

    public ModelAndView mascotaPerdida(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();

        PublicacionMascotaPerdida publicacion=  this.publicacionMascotaPerdidaRepositorio.buscar(new Integer(request.params("id")));
        parametros.put("publicacion", publicacion);

        Rescatista rescatista = publicacion.getFormulario();

        Contacto contacto = rescatista.getContacto();


        String estado = request.queryParams("estado");

        String usuario = request.session().attribute("usuarioLogeado");

        int idUsuario = this.repositorioUsuario.usuarioID(usuario);

        Usuario interesado = this.repoUsuario.buscar(idUsuario);


        if (estado != null) {
            if (estado.equals("si")) {
                    contacto.notificar("El dueño quiere recuperar a su mascota:\n"+"Comuniquese con:\n"+
                            "Nombre y Apellido del Dueño: "+ interesado.getNombre() +" " + interesado.getApellido() + "\n"+
                            "Datos del Contacto: "+ "\n"+
                            "Telefono: +5491162805877" + "\n" +
                            "Mail: katerinacejas3@gmail.com");

                    publicacionMascotaPerdidaRepositorio.eliminar(publicacion);
                }
            response.redirect("/esMiMascotaConExito");
        }
        return new ModelAndView(parametros, "/verMascotaPerdida.hbs");
    }

    public ModelAndView mascotasPerdidasSU(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();

        List<PublicacionMascotaPerdida> publicacionMascotaPerdidas = this.publicacionMascotaPerdidaRepositorio.buscarTodos();

        parametros.put("publicacion",publicacionMascotaPerdidas);
        return new ModelAndView(parametros,"/mascotasPerdidasSU.hbs");
    }

    public ModelAndView mascotaPerdidaSU(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();

        PublicacionMascotaPerdida publicacion = this.publicacionMascotaPerdidaRepositorio.buscar(new Integer(request.params("id")));

        parametros.put("publicacion", publicacion);

        return new ModelAndView(parametros, "verMascotaPerdidaSU.hbs");
    }

    public ModelAndView mostrarTodos(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        List<Mascota> mascotas = this.repositorio.buscarTodos();
        parametros.put("mascota", mascotas);
        return new ModelAndView(parametros, "adoptarUnaMascota.hbs");
    }

    public ModelAndView adoptarMascota(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        Mascota mascota = this.repositorio.buscar(new Integer(request.params("id")));
        parametros.put("mascota", mascota);
        List<Contacto> contactos = mascota.getDuenio().getListaContacto();
        String estado = request.queryParams("adopcion");
        String usuario = request.session().attribute("usuarioLogeado");
        int idUsuario = this.repositorioUsuario.usuarioID(usuario);
        Usuario interesado = this.repoUsuario.buscar(idUsuario);
        if (estado != null) {
            if (estado.equals("Adoptar")) {
                for(Contacto contacto : contactos) {
                    contacto.notificar("Hay un interesado en adoptar a su mascota:\n"+"Comuniquese con:\n"+
                            "Nombre y Apellido del Interesado: "+ interesado.getNombre() +" " + interesado.getApellido() + "\n"+
                            "Datos del Contacto: "+ "\n"+
                            "Telefono: +5491162805877"+ "\n"+
                            "Mail: katerinacejas3@gmail.com");
                }
            }
            response.redirect("/adopcionExitosa");
        }
            return new ModelAndView(parametros, "adoptarMascota.hbs");
    }

    public ModelAndView quererDarEnAdopcion(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("preguntasOpcionales", repositorioPreguntas.buscarTodos());

        response.redirect("/formularioQuererDarEnAdopcionMascota");
        return new ModelAndView(parametros, "formularioQuererDarEnAdopcionMascota.hbs");
    }

    public ModelAndView quererDarEnAdopcionExito(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "quererDarEnAdopcionExito.hbs");
    }


}