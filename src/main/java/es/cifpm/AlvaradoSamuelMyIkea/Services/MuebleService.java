package es.cifpm.AlvaradoSamuelMyIkea.Services;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Producto;
import es.cifpm.AlvaradoSamuelMyIkea.Repositorios.MuebleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MuebleService {
    private MuebleRepository muebleRepository;

    public static List<Producto> carrito = new ArrayList<Producto>() {};

    @Autowired
    public MuebleService(MuebleRepository muebleRepository) {
        this.muebleRepository = muebleRepository;
    }

    public List<Producto> getListaMuebles() {
        return muebleRepository.findAll();
    }

    public void guardarMueble(Producto producto){
        muebleRepository.save(producto);
    }

    public static List<Producto> getCarrito() {
        return carrito;
    }

    public static void agregarMuebleCarrito(Producto producto){
        carrito.add(producto);
    }

    public static void eliminarMuebleCarrito(Producto producto){
        carrito.removeIf(mueble1 -> mueble1.getProduct_id() == producto.getProduct_id());
    }

    public static void vaciarCarrito(){
        carrito.clear();
    }
}
