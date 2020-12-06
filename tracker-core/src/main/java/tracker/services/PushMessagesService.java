package tracker.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class PushMessagesService {

    // Накапливаемые данные
    private static final BlockingDeque<String> localQueue =  new LinkedBlockingDeque<>(100);
    private int count;

    // Получаем очередную строку из хранилища
    public void getDataFromStore(String line) throws InterruptedException {
        localQueue.put(line);
    }

    // Передаём все данные из очереди на сервер (пока не реализовано
    @Scheduled(cron = "${cron.pushData}")
    void pushData() {
        while (!localQueue.isEmpty()) {
            localQueue.poll(); // Куда-то отдаём
            System.out.println("Отправили куда-то точку "+count++);
        }
    }
}
