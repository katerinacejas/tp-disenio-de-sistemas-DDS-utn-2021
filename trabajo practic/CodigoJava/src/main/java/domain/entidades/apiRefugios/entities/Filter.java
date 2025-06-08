package domain.entidades.apiRefugios.entities;

import java.util.List;

public interface Filter {
    public List<Hogar> filtrar(List<Hogar> hogares);
}
