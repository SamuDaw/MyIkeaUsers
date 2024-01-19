package es.cifpm.AlvaradoSamuelMyIkea.Controllers;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Producto;
import es.cifpm.AlvaradoSamuelMyIkea.Models.Municipio;
import es.cifpm.AlvaradoSamuelMyIkea.Models.Provincia;
import es.cifpm.AlvaradoSamuelMyIkea.Services.MuebleService;
import es.cifpm.AlvaradoSamuelMyIkea.Services.MunicipioService;
import es.cifpm.AlvaradoSamuelMyIkea.Services.ProvinciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class MueblesController {

    private final MuebleService muebleService;
    private final MunicipioService municipioService;
    private final ProvinciaService provinciaService;

    @Autowired
    public MueblesController(MuebleService muebleService, MunicipioService municipioService, ProvinciaService provinciaService) {
        this.muebleService = muebleService;
        this.municipioService = municipioService;
        this.provinciaService = provinciaService;
    }

    @GetMapping
    public String muebles(Model model) {
        model.addAttribute("listaMuebles", muebleService.getListaMuebles());
        model.addAttribute("listaMunicipios", municipioService.getListaMunicipios());
        model.addAttribute("listaProvincias", provinciaService.getListaProvincias());
        return "/productos";
    }

    @GetMapping("/details/{id}")
    public String detalles(@PathVariable int id, Model model) {
        Optional<Producto> mueble = muebleService.getListaMuebles().stream().filter(mueble1 -> mueble1.getProduct_id() == id).findFirst();

        if (mueble.isPresent()) {

            Producto producto1 = mueble.get();
            Optional<Municipio> municipio = municipioService.getListaMunicipios().stream().filter(municipio1 -> producto1.getId_municipio() == municipio1.getId_municipio()).findFirst();

            if (municipio.isPresent()){

                Municipio municipio1 = municipio.get();
                Optional<Provincia> provincia = provinciaService.getListaProvincias().stream().filter(provincia1 -> municipio1.getId_provincia() == provincia1.getId_provincia()).findFirst();

                if (provincia.isPresent()){

                    Provincia provincia1 = provincia.get();
                    model.addAttribute("producto", producto1);
                    model.addAttribute("municipio", municipio1);
                    model.addAttribute("provincia", provincia1);

                    return "/details";

                }else {
                    return "redirect:/";
                }
            }else {
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/create")
    public  String crear(Model model) {

        model.addAttribute("producto",new Producto());
        model.addAttribute("listaProvincias", provinciaService.getListaProvincias());
        model.addAttribute("listaMunicipios", municipioService.getListaMunicipios());

    return "/create";
    }

    @PostMapping("/create")
    public String crear(@Valid @ModelAttribute("producto") Producto producto, @RequestParam("imageFile") MultipartFile productImage, @RequestParam("municipio") short id_municipio, BindingResult bindingResult){
        int prueba = producto.getId_municipio();
        if (bindingResult.hasErrors()){
            return "/create";
        }else {
            if (productImage != null && !productImage.isEmpty()){
                String nombreImagen = StringUtils.cleanPath(Objects.requireNonNull(productImage.getOriginalFilename()));
                Path directorioImg =  Paths.get("src//main//resources//static/img");
                String rutaAbsoluta = directorioImg.toFile().getAbsolutePath();

                try {
                    byte[] bytesImg = productImage.getBytes();
                    Path rutaCompleta = Paths.get(rutaAbsoluta + "/" + productImage.getOriginalFilename());
                    Files.write(rutaCompleta, bytesImg);
                    producto.setProduct_picture(productImage.getOriginalFilename());

                }catch(IOException e)
                {
                    System.out.println("Hermano, algo falla");
                    e.printStackTrace();
                }

            }
            producto.setId_municipio(id_municipio);
            muebleService.guardarMueble(producto);
            return "redirect:/productos";
        }
    }

}
