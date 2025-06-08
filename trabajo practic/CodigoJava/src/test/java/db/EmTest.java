package db;

import baseDeDatos.EntityManagerHelper;
import domain.entidades.mascota.*;
import domain.entidades.publicacion.PreguntaDarEnAdopcion;
import domain.entidades.publicacion.PreguntaObligatoria;
import domain.entidades.publicacion.PublicacionDarEnAdopcion;
import domain.entidades.publicacion.PublicacionMascotaPerdida;
import domain.entidades.usuario.*;
import domain.entidades.usuario.formasNotificacion.EstrategiaDeNotificacion;
import domain.entidades.usuario.formasNotificacion.Mail;
import domain.entidades.usuario.formasNotificacion.SMS;
import domain.entidades.usuario.formasNotificacion.Whatsapp;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmTest {

    @Test
    public void persistirAdminDios() {
        Administrador adminDios = new Administrador();
        adminDios.setContrasenia("adminDios");
        adminDios.setEmail("adminDios@gmail.com");
        adminDios.setUsuario("adminDios");
        adminDios.setApellido("dios");
        adminDios.setDocumento("123456789");
        adminDios.setDomicilio("Cordoba 1500");
        adminDios.setNombre("admin");
        adminDios.setnTramite("987654321");
        adminDios.setTipoDeDocumento(TipoDeDocumento.DNI);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(adminDios);
        EntityManagerHelper.commit();
    }

    @Test
    public void persistirCaracteristicasDeMascotas() {
        CaracteristicaDeMascotas caracteristica = new CaracteristicaDeMascotas();
        caracteristica.setCaracteristica("Amigable");
        CaracteristicaDeMascotas caracteristica2 = new CaracteristicaDeMascotas();
        caracteristica2.setCaracteristica("Gruñona");

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(caracteristica);
        EntityManagerHelper.getEntityManager().persist(caracteristica2);
        EntityManagerHelper.commit();
    }

    @Test
    public void persistirPreguntasNoObligatorias() {
        PreguntaDarEnAdopcion pregunta1 = new PreguntaDarEnAdopcion();
        pregunta1.setPregunta("tiene todas las vacunas al dia");
        pregunta1.setEsPreguntaDeRespuestaLibre(false);
        List<String> respuestas1 = new ArrayList<>();
        respuestas1.add("Si");
        respuestas1.add("No");
        pregunta1.setRespuesta(respuestas1);

        PreguntaDarEnAdopcion pregunta2 = new PreguntaDarEnAdopcion();
        pregunta2.setPregunta("cual es el motivo");
        pregunta2.setEsPreguntaDeRespuestaLibre(true);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(pregunta1);
        EntityManagerHelper.getEntityManager().persist(pregunta2);
        EntityManagerHelper.commit();
    }



}