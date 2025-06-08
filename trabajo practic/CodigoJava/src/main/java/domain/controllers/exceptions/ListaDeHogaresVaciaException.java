package controllers.exceptions;

public class ListaDeHogaresVaciaException extends RuntimeException{
    public ListaDeHogaresVaciaException(String mensaje) {
        super(mensaje);
    }
}
