package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan({"main","controllers"})
public class ServerCoreApplication {

    public static void main(String... args) {
        SpringApplication.run(ServerCoreApplication.class, args);
    }

}
