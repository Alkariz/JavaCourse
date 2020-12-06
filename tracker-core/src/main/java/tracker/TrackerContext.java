package tracker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
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
    public TaskScheduler poolScheduler() {
        //return null;
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("poolScheduler");
        scheduler.setPoolSize(20);
        return scheduler;
    }
}
