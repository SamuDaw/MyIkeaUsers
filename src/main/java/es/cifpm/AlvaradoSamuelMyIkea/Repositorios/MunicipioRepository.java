package es.cifpm.AlvaradoSamuelMyIkea.Repositorios;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {
    @Query("SELECT m FROM Municipio m JOIN FETCH m.provincia")
    List<Municipio> findAllMunicipiosWithProvincia();
}
