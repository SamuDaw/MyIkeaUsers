package es.cifpm.AlvaradoSamuelMyIkea.Services;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Carrito;
import es.cifpm.AlvaradoSamuelMyIkea.Models.Producto;
import es.cifpm.AlvaradoSamuelMyIkea.Repositorios.CarritoRepository;
import es.cifpm.AlvaradoSamuelMyIkea.Repositorios.MuebleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarritoService {

    private CarritoRepository carritoRepository;

    @Autowired
    public CarritoService(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    public List<Producto> getCarrito(Long carrito_id) {
       Optional<Carrito> carritoEncontrado = carritoRepository.findAll().stream().filter(carrito -> Objects.equals(carrito.getCarrito_id(), carrito_id)).findFirst();
       List<Producto> carrito = carritoEncontrado.get().getProductos();
       return carrito;
    }

/*    public void addMuebleCarrito(Producto producto, Long carrito_id) {
        Optional<Carrito> carritoEncontrado = carritoRepository.findAll().stream().filter(carrito -> Objects.equals(carrito.getCarrito_id(), carrito_id)).findFirst();
        List<Producto> carrito = carritoEncontrado.get().getProductos();
        carrito.add(producto);
        carritoRepository.save(carritoRepository.findAll().stream().filter(carrito -> Objects.equals(carrito.getCarrito_id(), carrito_id)).findFirst());
    }

    public void eliminarMuebleCarrito(Producto producto) {
        carrito.getProductos().remove(producto);
    }*/
}

