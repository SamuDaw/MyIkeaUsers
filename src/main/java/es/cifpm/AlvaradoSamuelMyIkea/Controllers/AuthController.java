package es.cifpm.AlvaradoSamuelMyIkea.Controllers;

import es.cifpm.AlvaradoSamuelMyIkea.Models.User;
import es.cifpm.AlvaradoSamuelMyIkea.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {

private UserService userService;

public AuthController(UserService userService){
    this.userService = userService;
}

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(){

    return "redirect:/productos";
}

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("register/save")
    public String registro(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model)
    {
        Optional<User> userExistente = userService.findByUsername(user.getUsername());

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        if (!userExistente.isEmpty()) {
            bindingResult.rejectValue("username", null, "Ya existe una cuenta con el Username: " + user.getUsername());
        }
        User usuario = userExistente.get();
        userService.saveUser(usuario);

        return "redirect:/register?success";

    }

    @GetMapping("users")
    public String users(Model model){
        List<User> usuarios = userService.findAllUsers();
        model.addAttribute("usuarios", usuarios);
        return "users";
    }
}