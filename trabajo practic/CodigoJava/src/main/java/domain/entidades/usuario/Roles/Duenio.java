package domain.entidades.usuario.Roles;

import domain.entidades.mascota.Mascota;
import domain.entidades.usuario.Persona;

public class Duenio {
    private Persona persona;
    private Mascota mascota;


    public Duenio() {

    }

    public static class DuenioDTO {
        public Mascota mascota;
        public Persona persona;

        public DuenioDTO(Persona persona, Mascota mascota) {
            this.persona = persona;
            this.mascota = mascota;
        }
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Persona getPersona() {
        return persona;
    }

    public Mascota getMascota() {
        return mascota;
    }
}