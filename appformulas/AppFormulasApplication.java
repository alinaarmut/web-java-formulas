package org.example.appformulas;

import jakarta.validation.constraints.NotNull;
import org.example.appformulas.essence.TypeRole;
import org.example.appformulas.essence.User;
import org.example.appformulas.essence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AppFormulasApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppFormulasApplication.class, args);
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/api/v1/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(true)
                        .maxAge(1800);
            }
        };
    }
    @Bean
    public CommandLineRunner run(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            if (userRepository.findByRole(TypeRole.ADMIN).isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(TypeRole.ADMIN);
                admin.setFirstName("Admin");
                admin.setLastName("Admin");
                admin.setEmail("admin@email.com");
                admin.setMiddleName("Admin");
                userRepository.save(admin);
                System.out.println("Первый администратор создан");
            }

            if (userRepository.findByRole(TypeRole.MODERATOR).isEmpty()) {
                User moderator = new User();
                moderator.setUsername("moderator");
                moderator.setPassword(passwordEncoder.encode("moderator123"));
                moderator.setRole(TypeRole.MODERATOR);
                moderator.setFirstName("Moderator");
                moderator.setLastName("Moderator");
                moderator.setEmail("moderator@email.com");
                moderator.setMiddleName("Moderator");
                userRepository.save(moderator);
                System.out.println("Первый модератор создан");
            }
        };
    }



}

