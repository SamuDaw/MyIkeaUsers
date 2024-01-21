package es.cifpm.AlvaradoSamuelMyIkea.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carritos")
public class Carrito {
    @Id
    @Column(name = "carrito_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carrito_id;

    @OneToOne(mappedBy = "carrito")
    private User usuario;

    @ManyToMany
    @JoinTable(
            name = "carrito_productos",
            joinColumns = @JoinColumn(name = "carrito_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productos;

    public Carrito(){
        this.productos = new ArrayList<>();
    }

    public Long getCarrito_id() {
        return carrito_id;
    }

    public void setCarrito_id(Long carrito_id) {
        this.carrito_id = carrito_id;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuarios) {
        this.usuario = usuarios;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
