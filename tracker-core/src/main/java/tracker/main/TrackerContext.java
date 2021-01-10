package tracker.main;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;
import tracker.controllers.TrackerRestTemplate;
import tracker.services.GPSService;
import tracker.services.PushMessagesService;
import tracker.services.StoreGPSDataService;

@Configuration
@EnableScheduling
@PropertySource("classpath:/app.properties")
public class TrackerContext {

    @Bean
    public GPSService takeDataService() {
        return new GPSService();
    }

    @Bean
    public PushMessagesService pushMsgsService() {
        return new PushMessagesService();
    }

    @Bean
    public StoreGPSDataService storeDataService() {
        return new StoreGPSDataService();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public TaskScheduler poolScheduler() {
        //return null;
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("poolScheduler");
        scheduler.setPoolSize(20);
        return scheduler;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public TrackerRestTemplate trackerRestTemplate() {
        return new TrackerRestTemplate();
    }
}
