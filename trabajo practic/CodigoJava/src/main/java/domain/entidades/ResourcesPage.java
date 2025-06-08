package domain.entidades;

import javax.persistence.*;

@Entity
@Table(name = "resourcesPage")
public class ResourcesPage {
    @Id
    @GeneratedValue
    private int id_caracteristicaDeMascotas;

    @Column(name = "rutaBanner")
    private String rutaBanner;

    @Column(name = "rutaLogo")
    private String rutaLogo;

    public String getRutaBanner() {
        return rutaBanner;
    }

    public void setRutaBanner(String rutaBanner) {
        this.rutaBanner = rutaBanner;
    }

    public String getRutaLogo() {
        return rutaLogo;
    }

    public void setRutaLogo(String rutaLogo) {
        this.rutaLogo = rutaLogo;
    }
}
