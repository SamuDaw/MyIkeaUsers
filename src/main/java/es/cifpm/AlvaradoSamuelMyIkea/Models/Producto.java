package es.cifpm.AlvaradoSamuelMyIkea.Models;

import jakarta.persistence.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

@Entity
@Table(name = "productoffer")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int product_id;

    @Column(name = "product_name", nullable = false, length = 512)
    private String product_name;

    @Column(name = "product_price",nullable = false)
    private float product_price;

    @Column(name = "product_picture")
    private String product_picture;

    @Column(name = "product_stock",nullable = true)
    private int product_stock;

    @ManyToOne
    @JoinColumn(name = "id_municipio")
    private Municipio municipio;

    @ManyToMany(mappedBy = "productos")
    private List<Carrito> carritos;

    public Producto(int product_id) {
        this.product_id = product_id;
    }

    public Producto() {

    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public float getProduct_price() {
        return product_price;
    }

    public void setProduct_price(float product_price) {
        this.product_price = product_price;
    }

    public String getProduct_picture() {
        return product_picture;
    }

    public void setProduct_picture(String product_picture) {
        this.product_picture = product_picture;
    }

/*    public short getId_municipio() {
        return id_municipio;
    }

    public void setId_municipio(short id_municipio) {
        this.id_municipio = id_municipio;
    }*/

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public int getProduct_stock() {
        return product_stock;
    }

    public void setProduct_stock(int product_stock) {
        this.product_stock = product_stock;
    }

    public boolean imagenExiste() {

        final String dirImg = "img";
        String nombreImagen = getProduct_picture();

        Resource recurso = new ClassPathResource("static/" + dirImg + "/" + nombreImagen);
        return recurso.exists();

    }
}
