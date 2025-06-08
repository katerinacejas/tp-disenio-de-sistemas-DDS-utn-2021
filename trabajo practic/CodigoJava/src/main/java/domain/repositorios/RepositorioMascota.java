package domain.repositorios;

import domain.entidades.mascota.Mascota;
import domain.entidades.usuario.Usuario;
import domain.repositorios.daos.DAO;
import spark.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RepositorioMascota extends Repositorio<Mascota> {

    private List<Mascota> mascotas;

    public RepositorioMascota(DAO<Mascota> dao) {
        super(dao);
    }

    public void  agregarMascota(Mascota mascota) {
        mascotas.add(mascota);
    }

    // Filtra la mascota que tiene el id y la retorna
    public Mascota filtrarMascota(int id){
      return mascotas.stream().filter(v -> v.getId() == id).collect(Collectors.toList()).get(0);
    }

    public int tamanio() {
        return mascotas.size();
    }

    public List<Mascota> esMiMascota(List<Mascota> mascotas, Usuario usuario) {

        List<Mascota> misMascotas = new ArrayList<>();

        for (Mascota mascota : mascotas) {
            if (mascota.getDuenio() != null) {
                if (usuario != null) {
                    if (mascota.getDuenio().getId() == (usuario.getId())) {
                        misMascotas.add(mascota);
                    }
                }
            }
        }

        return misMascotas;
    }
}