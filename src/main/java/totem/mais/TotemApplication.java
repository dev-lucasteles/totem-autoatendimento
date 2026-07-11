package totem.mais;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import totem.mais.controller.TotemMAIS;

@SpringBootApplication
public class TotemApplication {

    private static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        // Inicia o Spring Boot 
        springContext = SpringApplication.run(TotemApplication.class, args);
        
        // Inicia a tela do JavaFX
        Application.launch(TotemMAIS.class, args);
    }

    // Método para o JavaFX conseguir pegar as classes do Spring Boot
    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }
}