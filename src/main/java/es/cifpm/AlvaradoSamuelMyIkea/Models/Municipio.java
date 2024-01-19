package es.cifpm.AlvaradoSamuelMyIkea.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "municipios")
public class Municipio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_municipio")
    private short id_municipio;

    @Column(name = "id_provincia")
    private short id_provincia;

    @Column(name = "cod_municipio")
    private int cod_municipio;

    @Column(name = "DC")
    private int DC;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    private Provincia provincia;


    public short getId_municipio() {
        return id_municipio;
    }

    public void setId_municipio(short id_municipio) {
        this.id_municipio = id_municipio;
    }

    public short getId_provincia() {
        return id_provincia;
    }

    public void setId_provincia(short id_provincia) {
        this.id_provincia = id_provincia;
    }

    public int getCod_municipio() {
        return cod_municipio;
    }

    public void setCod_municipio(int cod_municipio) {
        this.cod_municipio = cod_municipio;
    }

    public int getDC() {
        return DC;
    }

    public void setDC(int DC) {
        this.DC = DC;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }
}
