package es.cifpm.AlvaradoSamuelMyIkea.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                    .requestMatchers("/403").permitAll()
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/register").permitAll()
                    .requestMatchers("/register/save").permitAll()
                    .requestMatchers("/users/**").hasRole("ADMIN")
                    .requestMatchers("/productos/details/**").hasRole("USER")
                    .requestMatchers("/pedidos/**").hasRole("USER")
                    .requestMatchers("/productos/create").hasAnyRole("MANAGER","ADMIN")
                    .requestMatchers("/productos/editar/**").hasAnyRole("MANAGER","ADMIN")
                    .requestMatchers("/carrito/**").hasRole("USER")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .permitAll()
                .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutUrl("/logout")
                    .addLogoutHandler(new SecurityContextLogoutHandler())
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                .and()
                .build();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}

