package es.cifpm.AlvaradoSamuelMyIkea.Models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pedido_id")
    private int pedido_id;

    @Column(name = "fecha_pedido", nullable = false)
    private LocalDate fecha_pedido;

    @Column(name = "precio_total", nullable = false)
    private float precio_total;

    @ManyToMany
    @JoinTable(
            name = "pedido_mueble",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "mueble_id")
    )
    private List<Producto> productos;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Pedido() {
        this.productos = new ArrayList<>();
    }

    public int getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(int pedido_id) {
        this.pedido_id = pedido_id;
    }

    public LocalDate getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(LocalDate fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public float getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(float precio_total) {
        this.precio_total = precio_total;
    }

    public List<Producto> getMuebles() {
        return productos;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
