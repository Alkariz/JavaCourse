package tracker.controllers;

import DTO.Point;
import com.fasterxml.jackson.core.JsonProcessingException;
import controllers.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;
import tracker.services.GPSService;
import tracker.services.PushMessagesService;
import tracker.services.StoreGPSDataService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TrackerRestTemplate {

    private static final Logger log = LoggerFactory.getLogger(PushMessagesService.class);
    private static final List<Point> pointsList = new ArrayList<>();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PushMessagesService pushMessagesService;

    @Autowired
    private StoreGPSDataService storeGPSDataService;

    @Autowired
    private GPSService aGPSService;

    // Для тестирования работы приложения
    @RequestMapping(value = "showPoints", method = RequestMethod.GET)
    public String showPoints() {
        String s = "Количество точек = " + pointsList.size();
        for (int i = 0; i < pointsList.size(); i++) {
            s += "<br>" + pointsList.get(i).toString() + "</br>";
        }
        return s;
    }

    @Scheduled(cron = "${cron.pushData}")
    public boolean takeThis() {
        boolean result=true;
        int currentPointsCount = pushMessagesService.pointsReadyCount();
        for (int i = 0; i < currentPointsCount; i++) {
            Point point = pushMessagesService.getLast();
            if (point == null) {
                return false;
            }
            pointsList.add(point);
            ResponseMessage responseMessage = restTemplate.postForObject("http://localhost:8081/takeThis", point, ResponseMessage.class);
            result = result && responseMessage.isSuccess();
        }
        return result;
    }

    @Scheduled(cron = "${cron.getData}")
    public boolean getData() throws InterruptedException, ParserConfigurationException, SAXException, ParseException, IOException {
        return aGPSService.getData();
    }

    @Scheduled(cron = "${cron.collectData}")
    public boolean collectData () throws JsonProcessingException {
        return storeGPSDataService.collectData();
    }
}