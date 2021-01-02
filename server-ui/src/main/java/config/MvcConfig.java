package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/routes").setViewName("routes");
                registry.addViewController("/payments").setViewName("payments");
                registry.addViewController("/registerClient").setViewName("registerClient");
                registry.addViewController("/registerManager").setViewName("registerManager");
                registry.addViewController("/login").setViewName("login");
                // ??? Как я понимаю, здесь будут жить стили текстов. Нужен ли к ним такой доступ?
                registry.addViewController("/css").setViewName("css");
                // ??? Как я понимаю, здесь будут жить картинки. Нужен ли к ним такой доступ?
                registry.addViewController("/img").setViewName("img");

                registry.addViewController("/home").setViewName("home");
                registry.addViewController("/").setViewName("home");
        }
}
