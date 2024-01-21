package es.cifpm.AlvaradoSamuelMyIkea.Services;

import es.cifpm.AlvaradoSamuelMyIkea.Models.User;
import es.cifpm.AlvaradoSamuelMyIkea.Repositorios.RoleRepository;
import es.cifpm.AlvaradoSamuelMyIkea.Repositorios.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userEncontrado = userRepository.findByUsername(username);
        User user = userEncontrado.get();
        if (userEncontrado.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado con nombre de usuario: " + username);
        }
        return (UserDetails) user;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

}