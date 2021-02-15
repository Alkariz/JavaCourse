package tracker.main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrackerCoreApplication implements CommandLineRunner {

    public static void main(String... args) {
        SpringApplication.run(TrackerCoreApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        read();
    }
}
