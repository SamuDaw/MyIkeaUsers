package es.cifpm.AlvaradoSamuelMyIkea.Controllers;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Producto;
import es.cifpm.AlvaradoSamuelMyIkea.Services.CarritoService;
import es.cifpm.AlvaradoSamuelMyIkea.Services.MuebleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
    private MuebleService muebleService;
    private CarritoService carritoService;

    @Autowired
    public CarritoController(CarritoService carritoService, MuebleService muebleService) {
        this.muebleService = muebleService;
        this.carritoService = carritoService;
    }

    @GetMapping
    public String verCarrito(Model model) {
        float precioTotal = 0;
        for (Producto producto : MuebleService.getCarrito()) {
            precioTotal += producto.getProduct_price();
        }
        model.addAttribute("carrito", MuebleService.getCarrito());
        model.addAttribute("precioTotal", precioTotal);
        return "/carrito";
    }

    @GetMapping("/comprar/{id}")
    public String comprarMueble(Model model, @PathVariable int id){
        Optional<Producto> muebleEncontrado = muebleService.getListaMuebles().stream().filter(mueble1 -> mueble1.getProduct_id() == id).findFirst();
        if (muebleEncontrado.isPresent()){
            Producto producto = muebleEncontrado.get();
            MuebleService.agregarMuebleCarrito(producto);
            return "redirect:/carrito";
        }else {
            return "redirect:productos/";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMueble(Model model, @PathVariable int id){
        Optional<Producto> muebleEncontrado = muebleService.getListaMuebles().stream().filter(mueble1 -> mueble1.getProduct_id() == id).findFirst();
        if (muebleEncontrado.isPresent()){
            Producto producto = muebleEncontrado.get();
            MuebleService.eliminarMuebleCarrito(producto);
            return "redirect:/carrito";
        }else {
            return "redirect:productos/";
        }
    }

}
