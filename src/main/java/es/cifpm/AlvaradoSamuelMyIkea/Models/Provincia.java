package es.cifpm.AlvaradoSamuelMyIkea.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "provincias")
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_provincia")
    private short id_provincia;

    @Column(name = "nombre", length = 30)
    private String nombre;

    @OneToMany(mappedBy = "provincia",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Municipio> municipios = new ArrayList<>();

    public short getId_provincia() {
        return id_provincia;
    }

    public void setId_provincia(short id_provincia) {
        this.id_provincia = id_provincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }
}
