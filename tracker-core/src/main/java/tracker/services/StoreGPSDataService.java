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

    private static final List<Point> allPointsQueue =  new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(PushMessagesService.class);


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
        int localCount=0;
        int pointsCount=currentGPSService.pointsCount();
        for (int i = 0; i < pointsCount; i++) {
            Point point = currentGPSService.givePoint();
            allPointsQueue.add(point);
            currentPushMessagesService.putPoint(point);
            log.info("Добавили точку в хранилище "+allPointsQueue.size());
            localCount++;
        }
        return localCount>0;
    }
}
