package security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

// Нужен, чтобы server-ui создавался на 81 порту
@Configuration
@PropertySource("classpath:/app.properties")
public class ServerUIContext {
}
