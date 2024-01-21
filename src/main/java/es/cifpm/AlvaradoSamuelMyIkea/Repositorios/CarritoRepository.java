package es.cifpm.AlvaradoSamuelMyIkea.Repositorios;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Carrito;
import es.cifpm.AlvaradoSamuelMyIkea.Models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long>{

}
