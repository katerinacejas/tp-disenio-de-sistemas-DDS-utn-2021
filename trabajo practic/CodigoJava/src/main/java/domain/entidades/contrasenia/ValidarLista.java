package domain.entidades.contrasenia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ValidarLista implements Criterio{

    @Override
    public boolean validar(String contrasenia) {

        try {
            File peoresContrasenias =  new File("CodigoJava/src/main/java/contrasenia/peoresContrasenias.txt");
            BufferedReader apc = new BufferedReader(new FileReader(peoresContrasenias));// apc = archivo peores contraseñas
            String contraseniaLeida;

            while ((contraseniaLeida = apc.readLine()) != null) {
                if (contrasenia.equals(contraseniaLeida)) {
                    return false;
                }
            }
        }
        catch (IOException e) {
        }
        return true;
    }
}