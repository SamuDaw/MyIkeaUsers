package es.cifpm.AlvaradoSamuelMyIkea.Controllers;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Carrito;
import es.cifpm.AlvaradoSamuelMyIkea.Models.Producto;
import es.cifpm.AlvaradoSamuelMyIkea.Models.User;
import es.cifpm.AlvaradoSamuelMyIkea.Services.CarritoService;
import es.cifpm.AlvaradoSamuelMyIkea.Services.MuebleService;
import es.cifpm.AlvaradoSamuelMyIkea.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
    private MuebleService muebleService;
    private CarritoService carritoService;

    private UserService userService;

    @Autowired
    public CarritoController(CarritoService carritoService, MuebleService muebleService, UserService userService) {
        this.muebleService = muebleService;
        this.carritoService = carritoService;
        this.userService = userService;
    }

    @GetMapping
    public String verCarrito(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombre = auth.getName();
        float precioTotal = 0;
        Carrito carrito = carritoService.getCarrito(nombre);
        for (Producto producto : carrito.getProductos()) {
            precioTotal += producto.getProduct_price();
        }
        
        model.addAttribute("nombre",nombre);
        model.addAttribute("productos_carrito", carritoService.getCarrito(nombre).getProductos());
        model.addAttribute("precioTotal", precioTotal);
        return "/carrito";
    }

    @GetMapping("/comprar/{id}")
    public String comprarMueble(Model model, @PathVariable int id){
        Optional<Producto> muebleEncontrado = muebleService.getListaMuebles().stream().filter(mueble1 -> mueble1.getProduct_id() == id).findFirst();

        if (muebleEncontrado.isPresent()){
            //Sacar el producto
            Producto producto = muebleEncontrado.get();
            //Coger el username del user autenticado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String usuario = auth.getName();
            //Coger el carrito de dicho usuario
            Carrito carritoUsuario = carritoService.getCarrito(usuario);

            //añadir el producto al carrito y guardarlo
            carritoUsuario.getProductos().add(producto);
            carritoService.guardarCarrito(carritoUsuario);
            return "redirect:/carrito";
        }else {
            return "redirect:productos/";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMueble(Model model, @PathVariable int id){
        //Coger el username del user autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Carrito carritoUsuario = carritoService.getCarrito(username);
        Optional<Producto> productoEncontrado = carritoUsuario.getProductos().stream().filter(producto -> producto.getProduct_id() == id).findFirst();

        if (productoEncontrado.isPresent()){
            Producto producto = productoEncontrado.get();
            carritoUsuario.getProductos().remove(producto);
            carritoService.guardarCarrito(carritoUsuario);
            return "redirect:/carrito";
        }else {
            return "redirect:productos/";
        }
    }

}
