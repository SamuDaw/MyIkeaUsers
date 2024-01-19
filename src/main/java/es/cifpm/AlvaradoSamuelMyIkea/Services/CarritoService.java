package es.cifpm.AlvaradoSamuelMyIkea.Services;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Carrito;
import es.cifpm.AlvaradoSamuelMyIkea.Models.Producto;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {
    private Carrito carrito = new Carrito();

    public Carrito getCarrito() {
        return carrito;
    }

    public void addMuebleCarrito(Producto producto) {
        carrito.getProductos().add(producto);
    }

    public void eliminarMuebleCarrito(Producto producto) {
        carrito.getProductos().remove(producto);
    }
}

