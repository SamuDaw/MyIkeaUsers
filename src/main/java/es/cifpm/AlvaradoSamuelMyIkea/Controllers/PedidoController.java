package es.cifpm.AlvaradoSamuelMyIkea.Controllers;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Carrito;
import es.cifpm.AlvaradoSamuelMyIkea.Models.Pedido;
import es.cifpm.AlvaradoSamuelMyIkea.Models.User;
import es.cifpm.AlvaradoSamuelMyIkea.Services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    private final CarritoService carritoService ;
    private final PedidoService pedidoService;

    private final UserService userService;


    @Autowired
    public PedidoController(CarritoService carritoService, PedidoService pedidoService,UserService userService) {
        this.carritoService = carritoService;
        this.pedidoService = pedidoService;
        this.userService = userService;
    }

    @GetMapping
    public String listarPedidos(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        List<User> usuarios = userService.findAllUsers();
        Optional<User> loggedUserEncontrado = usuarios.stream().filter(user -> Objects.equals(user.getUsername(), loggedUsername)).findFirst();
        if (loggedUserEncontrado.isEmpty()){
            return "redirect:/productos";
        }
        Long user_id = loggedUserEncontrado.get().getUser_id();

        model.addAttribute("listaPedidos",pedidoService.getPedidosUser(user_id));
        return "pedidos";
    }


    @PostMapping("/create")
    public String crearPedido(@Valid @ModelAttribute("pedido")Pedido pedido, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "carrito";
        }else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            List<User> usuarios = userService.findAllUsers();
            Optional<User> loggedUserEncontrado = usuarios.stream().filter(user -> Objects.equals(user.getUsername(), username)).findFirst();
            if (loggedUserEncontrado.isEmpty()){
                return "pedidos";
            }

            pedido.setUser(loggedUserEncontrado.get());
            pedidoService.guardarPedido(pedido);

            Carrito carritoUsuario = carritoService.getCarrito(username);
            carritoUsuario.getProductos().clear();
            carritoService.guardarCarrito(carritoUsuario);
            return "redirect:/pedidos";
        }
    }

    @GetMapping("/detalles/{id}")
    public String detallesPedido(@PathVariable int id, Model model){
        Optional<Pedido> pedidoEncontrado = pedidoService.getListaPedidos().stream().filter(pedido1 -> pedido1.getPedido_id() == id).findFirst();
        if (pedidoEncontrado.isPresent()){
            Pedido pedido = pedidoEncontrado.get();

            model.addAttribute("pedido", pedido);
            return "/detalles";
        }else {
            return "redirect:/carrito";
        }
    }

}
