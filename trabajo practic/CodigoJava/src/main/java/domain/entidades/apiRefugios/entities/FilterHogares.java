package domain.entidades.apiRefugios.entities;

import java.util.ArrayList;
import java.util.List;


public class FilterHogares {
    List<Filter> listaDeFilters;
    List<Hogar> listaFiltradaDeHogares;

    public FilterHogares(List<Filter> listaDeFilters) {
        this.listaDeFilters = listaDeFilters;
    }
    public List<Hogar> filtrarHogares(List<Hogar> hogares) {
        listaFiltradaDeHogares = hogares;
        for (Filter filter : listaDeFilters) {
            listaFiltradaDeHogares = filter.filtrar(listaFiltradaDeHogares);
        }
        return listaFiltradaDeHogares;
    }

    public void setListaDeFilters(Filter filter) {
        this.listaDeFilters.add(filter);
    }
}
