package es.cifpm.AlvaradoSamuelMyIkea.Controllers;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Producto;
import es.cifpm.AlvaradoSamuelMyIkea.Models.Municipio;
import es.cifpm.AlvaradoSamuelMyIkea.Models.Provincia;
import es.cifpm.AlvaradoSamuelMyIkea.Models.User;
import es.cifpm.AlvaradoSamuelMyIkea.Services.MuebleService;
import es.cifpm.AlvaradoSamuelMyIkea.Services.MunicipioService;
import es.cifpm.AlvaradoSamuelMyIkea.Services.ProvinciaService;
import es.cifpm.AlvaradoSamuelMyIkea.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class MueblesController {

    private final MuebleService muebleService;
    private final MunicipioService municipioService;
    private final ProvinciaService provinciaService;

    private final UserService userService;

    @Autowired
    public MueblesController(MuebleService muebleService, MunicipioService municipioService, ProvinciaService provinciaService, UserService userService) {
        this.muebleService = muebleService;
        this.municipioService = municipioService;
        this.provinciaService = provinciaService;
        this.userService = userService;
    }

    @GetMapping
    public String muebles(Model model) {
        model.addAttribute("listaMuebles", muebleService.getListaMuebles());
        return "/productos";
    }

    @GetMapping("/details/{id}")
    public String detalles(@PathVariable int id, Model model) {
        Optional<Producto> mueble = muebleService.getListaMuebles().stream().filter(mueble1 -> mueble1.getProduct_id() == id).findFirst();

        if (mueble.isPresent()) {

            Producto producto1 = mueble.get();
            Optional<Municipio> municipio = municipioService.getListaMunicipios().stream().filter(municipio1 -> producto1.getMunicipio().getId_municipio() == municipio1.getId_municipio()).findFirst();

            if (municipio.isPresent()){

                Municipio municipio1 = municipio.get();
                Optional<Provincia> provincia = provinciaService.getListaProvincias().stream().filter(provincia1 -> municipio1.getProvincia().getId_provincia() == provincia1.getId_provincia()).findFirst();

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
        //int prueba = producto.getId_municipio();
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
            producto.setMunicipio(municipioService.getListaMunicipios().stream().filter(municipio -> municipio.getId_municipio() == id_municipio).findFirst().get());
            muebleService.guardarMueble(producto);
            return "redirect:/productos";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(Model model, @PathVariable(name = "id")int id_producto){
        Optional<Producto> productoEncontrado = muebleService.getListaMuebles().stream().filter(producto -> producto.getProduct_id() == id_producto).findFirst();
        if (productoEncontrado.isEmpty()){
            return "/productos";
        }
        Producto producto = productoEncontrado.get();

        model.addAttribute("id_provinciaPrevia", producto.getMunicipio().getProvincia().getId_provincia());
        model.addAttribute("id_municipioPrevio", producto.getMunicipio().getId_municipio());
        model.addAttribute("producto",producto);
        model.addAttribute("listaProvincias", provinciaService.getListaProvincias());
        model.addAttribute("listaMunicipios", municipioService.getListaMunicipios());
        return "/editar";

    }

    @PostMapping("/editar/{id}")
    public String editar(@Valid @ModelAttribute("producto") Producto producto, BindingResult bindingResult, @PathVariable(name = "id") int id,  @RequestParam("imageFile") MultipartFile productImage){
        if (bindingResult.hasErrors()){
            return "redirect:/productos";
        }else {
            Optional<Municipio> municipio = municipioService.getListaMunicipios().stream().filter(municipio1 -> municipio1.getId_municipio() == producto.getMunicipio().getId_municipio()).findFirst();
            if (municipio.isEmpty()){
                return "redirect:/productos";
            }
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
            producto.setProduct_id(id);
            producto.setMunicipio(municipio.get());
            muebleService.guardarMueble(producto);
            return "redirect:/productos";
        }
    }

}
