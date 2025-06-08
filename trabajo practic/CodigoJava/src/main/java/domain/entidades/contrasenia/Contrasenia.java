package domain.entidades.contrasenia;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

public class Contrasenia {

    public static boolean contraseniaValida(String contrasenia){

        List<Criterio> criterios = new ArrayList<>();
        List<Boolean> resultados = new ArrayList<>();

        ValidarCaracteres validarCaracteres = new ValidarCaracteres();
        ValidarLista validarLista = new ValidarLista();
        ValidarLongitud validarLongitud = new ValidarLongitud();

        criterios.add(validarCaracteres);
        criterios.add(validarLista);
        criterios.add(validarLongitud);

        for(Criterio criterio : criterios ) {
            resultados.add(criterio.validar(contrasenia));
        }

        boolean isAllTrue = resultados.stream().allMatch(valor -> valor);
        return isAllTrue;
    }

    public static String contraseniaHash(String contrasenia){
        String md5Contrasenia = DigestUtils.md5Hex(contrasenia);

        return md5Contrasenia;
    }

}


