package es.cifpm.AlvaradoSamuelMyIkea.Repositorios;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
