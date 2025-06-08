package domain.entidades.mascota;

import domain.repositorios.RepositorioMascota;
import domain.entidades.usuario.Contacto;
import domain.entidades.usuario.Persona;

import java.util.List;

public class Notificador {
    private RepositorioMascota lista;

    public void notificarDuenio(int id){
        Mascota mascotaPerdida = lista.filtrarMascota(id);
        mascotaPerdida.setPerdida(true); //setea a la mascota como perdida.
        Persona duenio = mascotaPerdida.getDuenio1();
        List<Contacto> listaContactos = duenio.getListaContacto();
        listaContactos.forEach(v -> v.notificar("mensaje")); //TODO revisar que hacia esto para poner un mensaje acorde
    }
}