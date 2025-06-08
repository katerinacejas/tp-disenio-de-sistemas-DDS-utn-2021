package domain.controllers;

import domain.repositorios.RepositorioPersona;
import  domain.entidades.usuario.Persona;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class PersonaController {

    private static PersonaController instancia;
    private RepositorioPersona repositorio;

    public PersonaController(){
        this.repositorio = new RepositorioPersona();
    }





    public ModelAndView verificarPersona(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>(); // TODO ACA HABRIA QUE VERIFICAR SI LOS DATOS ESTAN EN LA BASE DE DATOS Y SI NO ESTAR TIRAR UNA EXCEPTION CALCULO
        return new ModelAndView(parametros,"/registrarMascotaSinUsuario.hbs"); //TODO SUBIR ESTOS DATOS A LA BASE DE DATOS
    }


    public ModelAndView verMascotas(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "/verMascotasSinUsuario.hbs");
    }


}
