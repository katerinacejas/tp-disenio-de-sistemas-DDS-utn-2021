
import static spark.Spark.*;

import domain.entidades.publicacion.RecomendacionesSemanales;
import server.Router;
import spark.debug.DebugScreen;


public class Server {
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        Router.init();
        DebugScreen.enableDebugScreen();
        RecomendacionesSemanales recomendaciones = new RecomendacionesSemanales();
        recomendaciones.getTarea().run();
    }


    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 8000; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}