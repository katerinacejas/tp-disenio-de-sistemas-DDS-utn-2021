package domain.entidades.apiRefugios.entities;

import java.util.ArrayList;
import java.util.List;

public class ListaDeHogares {
    public int total;
    public int offset;
    public List<Hogar> hogares = new ArrayList<>();

    public int getTotal() {
        return total;
    }
}
