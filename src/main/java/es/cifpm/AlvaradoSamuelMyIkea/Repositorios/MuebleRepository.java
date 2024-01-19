package es.cifpm.AlvaradoSamuelMyIkea.Repositorios;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuebleRepository extends JpaRepository<Producto, Integer> {

}
