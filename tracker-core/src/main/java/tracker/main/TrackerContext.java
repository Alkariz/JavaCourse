package tracker.main;

import dao.PointDAO;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;
import tracker.controllers.PointMapper;
import tracker.controllers.PointService;
import tracker.controllers.TrackerRestTemplate;
import tracker.services.GPSService;
import tracker.services.PushMessagesService;
import tracker.services.StoreGPSDataService;

@Configuration
@EnableScheduling
@EnableJpaRepositories("dao.repo")
@EntityScan(basePackageClasses = PointDAO.class)
@PropertySource("classpath:/application.properties")
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
    public PointMapper pointMapper() { return new PointMapper(); }

    @Bean
    public PointService pointService() { return new PointService(); }

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
