package es.cifpm.AlvaradoSamuelMyIkea.Models;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private List<Producto> carrito;

    public Carrito(){
        this.carrito = new ArrayList<>();
    }

    public List<Producto> getCarrito() {
        return carrito;
    }

    public void addMuebleCarrito(Producto producto){
        getCarrito().add(producto);
    }

    public void eliminarMuebleCarrito(Producto producto){
        getCarrito().remove(producto);
    }

    public void setCarrito(List<Producto> carrito) {
        this.carrito = carrito;
    }
}
