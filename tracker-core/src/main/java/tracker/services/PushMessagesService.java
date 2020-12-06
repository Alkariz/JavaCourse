package tracker.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class PushMessagesService {

    private static final Logger log = LoggerFactory.getLogger(PushMessagesService.class);

    // Накапливаемые данные
    private static final BlockingDeque<String> localQueue =  new LinkedBlockingDeque<>(100);
    // Локальная переменная для обозначения текущей использованной точки
    private int count;

    // Получаем очередную строку из хранилища
    public void getDataFromStore(String line) throws InterruptedException {
        localQueue.put(line);
    }

    // Передаём все данные из очереди на сервер (пока не реализовано
    @Scheduled(cron = "${cron.pushData}")
    void pushData() {
        while (!localQueue.isEmpty()) {
            log.info("Отправили куда-то точку "+count++);
            String s = localQueue.poll(); // Куда-то отдаём
            log.info(s);
        }
    }
}
