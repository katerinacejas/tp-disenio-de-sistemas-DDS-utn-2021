package domain.entidades.mascota;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "caracteristicaDeMascotas")
public class CaracteristicaDeMascotas {
    @Id
    @GeneratedValue
    private int id_caracteristicaDeMascotas;

    @Column(name = "caracteristica")
    private String caracteristica;

    public int getId() {
        return id_caracteristicaDeMascotas;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

}