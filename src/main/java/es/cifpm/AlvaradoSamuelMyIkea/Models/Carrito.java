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

    @ManyToMany
    @JoinTable(
            name = "user_carrito",
            joinColumns = @JoinColumn(name = "carrito_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> usuarios;

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

    public List<Producto> getProductos() {
        return productos;
    }

    public void addMuebleCarrito(Producto producto){
        getProductos().add(producto);
    }

    public void eliminarMuebleCarrito(Producto producto){
        getProductos().remove(producto);
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
