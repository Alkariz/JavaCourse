package tracker.services;

import DTO.Point;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StoreGPSDataService {

//    private static final BlockingDeque<String> allDataQueue =  new LinkedBlockingDeque<>(100);
    private static final List<Point> allPointsQueue =  new ArrayList<>();
//    private static final List<String> allData = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(PushMessagesService.class);

    // Локальная переменная для обозначения текущей использованной точки
    private int count;

    /**
    * Iндекс последнего отправленного сообщения. Нужен?
     */
    private int lastSentMessageIndex;

//    public boolean haveNewData() {
//        return lastSentMessageIndex!=allDataQueue.size();
//    }

    @Autowired
    private GPSService currentGPSService;

    @Autowired
    private PushMessagesService currentPushMessagesService;

    /**
    * Раз в cron.collectData получаем данные из сервиса ГПС, добавляем во внутренний список всех строк
     * Эту же строку передаём в сервис передачи сообщений
     */
//    @Scheduled(cron = "${cron.collectData}")
    public boolean collectData () throws JsonProcessingException {
        // Пока есть, что отдать - берём и добавляем
        while (currentGPSService.haveData()) {
            Point point = currentGPSService.givePoint();
            allPointsQueue.add(point);
            currentPushMessagesService.putPoint(point);
            log.info("Добавили точку в хранилище "+count++);
        }
        return true;
    }
}
