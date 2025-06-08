package domain.entidades.publicacion;

import domain.entidades.usuario.Contacto;
import domain.entidades.usuario.DatosBasicos;

public class RescatistaFormulario extends DatosBasicos  {

    private Contacto contacto;
    private String fotoMascota;
    private String lugarEncontrada;

    public Contacto getContacto() {
        return contacto;
    }
}
