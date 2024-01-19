package es.cifpm.AlvaradoSamuelMyIkea.Controllers;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Pedido;
import es.cifpm.AlvaradoSamuelMyIkea.Services.MuebleService;
import es.cifpm.AlvaradoSamuelMyIkea.Services.MunicipioService;
import es.cifpm.AlvaradoSamuelMyIkea.Services.PedidoService;
import es.cifpm.AlvaradoSamuelMyIkea.Services.ProvinciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    private final MuebleService muebleService;
    private final PedidoService pedidoService;


    @Autowired
    public PedidoController(MuebleService muebleService, PedidoService pedidoService) {
        this.muebleService = muebleService;
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public String listarPedidos(Model model){
        model.addAttribute("listaPedidos",pedidoService.getListaPedidos());
        return "pedidos";
    }

    @PostMapping("/create")
    public String crearPedido(@Valid @ModelAttribute("pedido")Pedido pedido, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "carrito";
        }else {
            pedidoService.guardarPedido(pedido);
            MuebleService.vaciarCarrito();
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
