package domain.entidades.contrasenia;

public class ValidarLongitud implements Criterio {

    private static Integer longitudMinima = 8;

    @Override
    public boolean validar(String contrasenia) {
        return contrasenia.length() > longitudMinima;
    }
}