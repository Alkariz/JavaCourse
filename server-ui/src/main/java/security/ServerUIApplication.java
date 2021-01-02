package security;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"security", "config"})
public class ServerUIApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerUIApplication.class, args);
    }
}
