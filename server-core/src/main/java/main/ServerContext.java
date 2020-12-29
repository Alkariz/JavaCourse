package main;

import controllers.ServerRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/app.properties")
public class ServerContext {

    @Bean
    public ServerRestTemplate serverRestTemplate() {
        return new ServerRestTemplate();
    }
}
