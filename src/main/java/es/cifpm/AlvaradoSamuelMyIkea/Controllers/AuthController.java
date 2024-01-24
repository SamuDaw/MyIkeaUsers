package es.cifpm.AlvaradoSamuelMyIkea.Controllers;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Carrito;
import es.cifpm.AlvaradoSamuelMyIkea.Models.User;
import es.cifpm.AlvaradoSamuelMyIkea.Repositorios.RoleRepository;
import es.cifpm.AlvaradoSamuelMyIkea.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class AuthController {

private UserService userService;
private PasswordEncoder passwordEncoder;
private RoleRepository roleRepository;

public AuthController(UserService userService, PasswordEncoder passwordEncoder, RoleRepository roleRepository){

    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
}

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(){
    return "redirect:/productos";
    }

    @GetMapping("/logout")
    public String logout() {
    return "redirect:/login?logout";
    }

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("register/save")
    public String registro(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email , Model model)
    {
        Optional<User> userNameExistente = userService.findByUsername(username);
        Optional<User> userEmailExistente = userService.findByUsername(username);

        if (userNameExistente.isPresent() || userEmailExistente.isPresent()) {
            return "redirect:/register?existe";
        }
        if((username.isEmpty()) || (password.isEmpty()) || (email.isEmpty())){
            return "redirect:/register?error";
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER").orElse(null)));
        newUser.setEmail(email);
        //userService.saveUser(newUser);
        // Crear un nuevo carrito para el usuario
        Carrito carritoUsuario = new Carrito();
        carritoUsuario.setUsuario(newUser);

        // Asignar el carrito al usuario
        newUser.setCarrito(carritoUsuario);

        userService.saveUser(newUser);
        return "redirect:/register?success";

    }


}