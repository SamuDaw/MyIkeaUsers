package es.cifpm.AlvaradoSamuelMyIkea.Services;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Pedido;
import es.cifpm.AlvaradoSamuelMyIkea.Repositorios.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PedidoService {
    private PedidoRepository pedidoRepository;


    @Autowired
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> getListaPedidos() {
        return pedidoRepository.findAll();
    }

    public void guardarPedido(Pedido pedido){
        pedidoRepository.save(pedido);
    }

}
