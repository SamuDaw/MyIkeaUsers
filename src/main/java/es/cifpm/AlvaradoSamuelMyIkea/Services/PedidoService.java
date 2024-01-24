package es.cifpm.AlvaradoSamuelMyIkea.Services;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Carrito;
import es.cifpm.AlvaradoSamuelMyIkea.Models.Pedido;
import es.cifpm.AlvaradoSamuelMyIkea.Models.User;
import es.cifpm.AlvaradoSamuelMyIkea.Repositorios.PedidoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class PedidoService {
    private PedidoRepository pedidoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> getListaPedidos() {
        return pedidoRepository.findAll();
    }
    public List<User> getUsuariosPedidos(){
        List<User> listaUsuarios = new ArrayList<>();
        pedidoRepository.findAll().forEach(pedido -> listaUsuarios.add(pedido.getUser()));
        return listaUsuarios;
    }

    public List<Pedido> getPedidosUser(Long user_id) {
        String consultaPedidos = "SELECT p FROM Pedido p JOIN FETCH p.user WHERE p.user.id = :user_id";
        TypedQuery<Pedido> query = entityManager.createQuery(consultaPedidos, Pedido.class);
        query.setParameter("user_id", user_id);
        return query.getResultList();
    }


    public void guardarPedido(Pedido pedido){
        pedidoRepository.save(pedido);
    }

}
