package domain.entidades.publicacion;

import domain.controllers.InteresadoController;
import domain.repositorios.Repositorio;
import domain.repositorios.RepositorioInteresados;
import domain.repositorios.RepositorioPublicacionAdoptar;
import domain.entidades.usuario.Interesado;
import domain.repositorios.factories.FactoryRepositorio;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import java.util.Timer;
import java.util.Calendar;
import java.util.*;

public class RecomendacionesSemanales {
    private Repositorio<PublicacionDarEnAdopcion> publicacionDarEnAdopcionRepositorio;
    private Repositorio<Interesado> interesadoRepositorio;
    //private Repositorio<PublicacionQuererAdoptarConComodidades> repoIntencionAdoptar;

    public RecomendacionesSemanales() {
        this.publicacionDarEnAdopcionRepositorio = FactoryRepositorio.get(PublicacionDarEnAdopcion.class);
        this.interesadoRepositorio = FactoryRepositorio.get(Interesado.class);
        this.timer = new Timer();
        //this.repoIntencionAdoptar = FactoryRepositorio.get(PublicacionQuererAdoptarConComodidades.class);
    }

    private int cincoMinutos = 60000; //esto es para 1 minuto
    // 5 minutos en milisegundos son 300000 CAMBIAR
    private Timer timer;

    public TimerTask getTarea() {
        return tarea;
    }

    private TimerTask tarea = new TimerTask() {
        @Override
        public void run() {
            for (Interesado interesado : interesadoRepositorio.buscarTodos()) {
                timer.schedule(tarea, 0, cincoMinutos);
                for (PublicacionDarEnAdopcion publicacion : publicacionDarEnAdopcionRepositorio.buscarTodos()) {
                    System.out.println("buscando en las publicaciones");
                    if (interesado.leSirve(publicacion)) {
                        System.out.println("encontre una publicacion. a punto de mandar notificacion");
                        interesado.enviarPublicacion();
                        System.out.println("Se envió la recomendación semanal");
                    }
                }
            }
            //timer.schedule(tarea, 0, cincoMinutos);
        }
    };

}


   /* public void enviarRecomendaciones() {
        timer.schedule(tarea, 0, cincoMinutos);
       if(this.esLunes()) {
            for(Interesado interesado : interesados.getInteresados()) {
                for(PublicacionAdoptar publicacion : publicaciones.getPublicacionesAdoptar()) {
                    if(interesado.leSirve(publicacion)) {
                        interesado.enviarPublicacion();
                    }
                }
            }
            System.out.println("Se envió la recomendación semanal");
        }
        else {
            System.out.println("No se envió ninguna recomendacion semanal porque no es lunes");
        }*/







    /*@Override
    public void execute(JobExecutionContext jobExecutionContext) {
        List<PublicacionQuererAdoptarConComodidades> adopcion = repoIntencionAdoptar.buscarTodos();

        for(PublicacionQuererAdoptarConComodidades p : adopcion) {
            List<PublicacionDarEnAdopcion> posibles = ControllerOfertaAdopcion.getOfertasAdopcionByParams(p.getPref());
            System.out.println("Posibles para la PublicacionDeseoAdoptar " + p.getId());
            posibles.forEach(t -> System.out.println(t.getMascota().getNombre() + " " + t.getMascota().getId()));
            if(posibles.size() > 0)
                p.getContactCard().forEach(c -> c.notificarTodosRecomendacionesAdopcion(posibles));
        }
    }*/






/*
public boolean esLunes() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
    }
*/




