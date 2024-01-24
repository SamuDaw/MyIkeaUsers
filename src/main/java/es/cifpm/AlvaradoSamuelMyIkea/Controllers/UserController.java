package es.cifpm.AlvaradoSamuelMyIkea.Controllers;

import es.cifpm.AlvaradoSamuelMyIkea.Models.User;
import es.cifpm.AlvaradoSamuelMyIkea.Repositorios.RoleRepository;
import es.cifpm.AlvaradoSamuelMyIkea.Services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;

    }

    @GetMapping("users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String users(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        List<User> usuarios = userService.findAllUsers();
        Optional<User> loggedUserEncontrado = usuarios.stream().filter(user -> Objects.equals(user.getUsername(), loggedUsername)).findFirst();
        if (loggedUserEncontrado.isEmpty()){
            return "redirect:/productos";
        }

        model.addAttribute("loggedUsername", loggedUsername);
        model.addAttribute("usuarios", usuarios);
        return "users";
    }

    @GetMapping("/users/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String eliminarUsers(Model model, @PathVariable Long id) {
        Optional<User> usuarioEncontrado = userService.findAllUsers().stream().filter(usuario -> Objects.equals(usuario.getUser_id(), id)).findFirst();
        if (usuarioEncontrado.isEmpty() || id == 1 || id == 2 || id == 3) {
            return "redirect:/users";
        }
        userService.borrarUsuario(usuarioEncontrado.get());
        return "redirect:/users";
    }
}
