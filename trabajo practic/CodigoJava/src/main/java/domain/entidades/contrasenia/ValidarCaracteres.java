package domain.entidades.contrasenia;

public class ValidarCaracteres implements Criterio {

    @Override
    public boolean validar(String contrasenia) {
        return !contrasenia.equals(contrasenia.toLowerCase());
    }

}
