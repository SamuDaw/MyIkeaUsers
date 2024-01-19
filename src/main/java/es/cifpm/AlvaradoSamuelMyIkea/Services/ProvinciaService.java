package es.cifpm.AlvaradoSamuelMyIkea.Services;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Municipio;
import es.cifpm.AlvaradoSamuelMyIkea.Models.Provincia;
import es.cifpm.AlvaradoSamuelMyIkea.Repositorios.MunicipioRepository;
import es.cifpm.AlvaradoSamuelMyIkea.Repositorios.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinciaService {
    private final ProvinciaRepository provinciaRepository;

    @Autowired
    public ProvinciaService(ProvinciaRepository provinciaRepository) {
        this.provinciaRepository = provinciaRepository;
    }

    public List<Provincia> getListaProvincias() {
        return provinciaRepository.findAll();
    }
}
