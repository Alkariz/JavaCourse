package tracker.services;

import DTO.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class PushMessagesService {

    private static final Logger log = LoggerFactory.getLogger(PushMessagesService.class);

    // Накапливаемые данные
    private static final BlockingDeque<String> localQueue =  new LinkedBlockingDeque<>(100);
    private static final BlockingDeque<Point> localPoints =  new LinkedBlockingDeque<>(100);
    // Локальная переменная для обозначения текущей использованной точки
    private int count;

    // Получаем очередную строку из хранилища
    public void getDataFromStore(String line) throws InterruptedException {
        localQueue.put(line);
    }

    public void getPointFromStore(Point point) throws InterruptedException {
        localPoints.put(point);
    }

    public Point getLast() {
        if (!localPoints.isEmpty()) {
            return localPoints.poll();
        }
        else {
            return null;
        }
    }

    // Передаём все данные из очереди на сервер
    //@Scheduled(cron = "${cron.pushData}")
    void pushData() {
        while (!localQueue.isEmpty()) {
//            DTO.Point point = restTemplate.getForObject(
//                    "http://services.groupkt.com/country/get/iso2code/RU", DTO.Point.class);
//            return point;

            String s = localQueue.poll();
//            restTemplate.postForObject("http://localhost:8080/takeThis", s, Point.class);
            log.info("Отправили куда-то точку "+count++);
//            String s = localQueue.poll(); // Куда-то отдаём
//            log.info(s);
        }
    }
}
