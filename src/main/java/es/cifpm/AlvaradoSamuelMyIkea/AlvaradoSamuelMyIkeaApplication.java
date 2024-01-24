package es.cifpm.AlvaradoSamuelMyIkea;

import es.cifpm.AlvaradoSamuelMyIkea.Models.Carrito;
import es.cifpm.AlvaradoSamuelMyIkea.Models.Role;
import es.cifpm.AlvaradoSamuelMyIkea.Models.User;
import es.cifpm.AlvaradoSamuelMyIkea.Repositorios.RoleRepository;
import es.cifpm.AlvaradoSamuelMyIkea.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@SpringBootApplication
public class AlvaradoSamuelMyIkeaApplication {

	private final RoleRepository roleRepository;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public AlvaradoSamuelMyIkeaApplication(RoleRepository roleRepository, UserService userService, PasswordEncoder passwordEncoder) {
		this.roleRepository = roleRepository;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(AlvaradoSamuelMyIkeaApplication.class, args);
	}

    @EventListener
	public void Seeder(ContextRefreshedEvent event){
		seedRoles();
		seedUsers();
	}

	private void seedRoles() {
		Arrays.asList("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN").forEach(roleName -> {
			if (!roleRepository.findByName(roleName).isPresent()) {
				Role role = new Role(roleName);
				roleRepository.save(role);
			}
		});
	}

	private void seedUsers() {
		if (userService.findByUsername("user").isEmpty()) {
			User adminUser = new User();
			adminUser.setUsername("user");
			adminUser.setPassword(passwordEncoder.encode("user"));
			adminUser.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER").orElse(null)));
			adminUser.setEmail(("user@myikea.com"));
			//userService.saveUser(adminUser);
			// Crear un nuevo carrito para el usuario
			Carrito carritoUsuario = new Carrito();
			carritoUsuario.setUsuario(adminUser);

			// Asignar el carrito al usuario
			adminUser.setCarrito(carritoUsuario);

			userService.saveUser(adminUser);
		}
		if (userService.findByUsername("manager").isEmpty()) {
			User adminUser = new User();
			adminUser.setUsername("manager");
			adminUser.setPassword(passwordEncoder.encode("manager"));
			adminUser.setRoles(Arrays.asList(roleRepository.findByName("ROLE_MANAGER").orElse(null)));
			adminUser.setEmail(("manager@myikea.com"));
			// Crear un nuevo carrito para el usuario
			Carrito carritoUsuario = new Carrito();
			carritoUsuario.setUsuario(adminUser);

			// Asignar el carrito al usuario
			adminUser.setCarrito(carritoUsuario);

			userService.saveUser(adminUser);
		}
		if (userService.findByUsername("admin").isEmpty()) {
			User adminUser = new User();
			adminUser.setUsername("admin");
			adminUser.setPassword(passwordEncoder.encode("admin"));
			adminUser.setRoles(Arrays.asList(roleRepository.findByName("ROLE_ADMIN").orElse(null)));
			adminUser.setEmail(("admin@myikea.com"));
			// Crear un nuevo carrito para el usuario
			Carrito carritoUsuario = new Carrito();
			carritoUsuario.setUsuario(adminUser);

			// Asignar el carrito al usuario
			adminUser.setCarrito(carritoUsuario);

			userService.saveUser(adminUser);
		}
	}
}
