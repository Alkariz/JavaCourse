package tracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


@Service
public class StoreGPSDataService {

    private static final BlockingDeque<String> allDataQueue =  new LinkedBlockingDeque<>(100);
    private static final List<String> allData = new ArrayList<>();

    private int count;

    /**
    * Iндекс последнего отправленного сообщения. Нужен?
     */
    private int lastSentMessageIndex;

    public boolean haveNewData() {
        return lastSentMessageIndex!=allDataQueue.size();
    }

    @Autowired
    private GPSService currentGPSService;

    @Autowired
    private PushMessagesService currentPushMessagesService;

    /**
    * Раз в cron.collectData получаем данные из сервиса ГПС, добавляем во внутренний список всех строк
     * Эту же строку передаём в сервис передачи сообщений
     */
    @Scheduled(cron = "${cron.collectData}")
    void CollectData () throws InterruptedException {
        // Пока есть, что отдать - берём и добавляем
        while (currentGPSService.haveData()) {
            String s = currentGPSService.giveData();
            allData.add(s);
            currentPushMessagesService.getDataFromStore(s);
            System.out.println("Добавили точку в хранилище "+count++);
        }
    }
}
