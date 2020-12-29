package tracker.controllers;

import DTO.Point;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;
import tracker.services.GPSService;
import tracker.services.PushMessagesService;
import tracker.services.StoreGPSDataService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TrackerRestTemplate {

    // Как я понимаю, счётчик полученных строк-точек
    private final AtomicLong counter = new AtomicLong();

    private static final Logger log = LoggerFactory.getLogger(PushMessagesService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PushMessagesService pushMessagesService;

    @Autowired
    private StoreGPSDataService storeGPSDataService;

    @Autowired
    private GPSService aGPSService;

    @RequestMapping(value = "/getPoint", method = RequestMethod.GET)
    public Point getPoint() {
        return pushMessagesService.getLast();
    }

    @Scheduled(cron = "${cron.pushData}")
//    @RequestMapping(value = "/qwe", method = RequestMethod.GET)
    public boolean takeThis() throws JsonProcessingException {
        boolean result=true;
        while (pushMessagesService.haveData()) {
            Point point = pushMessagesService.getLast();
            if (point == null) {
                return false;
            }
            ResponseMessage response = restTemplate.getForObject("http://localhost:8081/takeResp", ResponseMessage.class);
//            Point temp = restTemplate.postForObject("http://localhost:8081/takeThis", point, Point.class);
//            Response response = restTemplate.postForObject("http://localhost:8081/takeThis", point.toJson(), Response.class);
//            HttpHeaders headers = new HttpHeaders();
//            HttpEntity<String> entity = new HttpEntity<String>(point.toJson(),headers);
//            Response response = restTemplate.postForObject("http://localhost:8081/takeThis", entity, Response.class);
            ResponseMessage responseMessage = restTemplate.postForObject("http://localhost:8081/takeThis", point, ResponseMessage.class);
            log.info(responseMessage.message);
//            log.info(point.toJson());
            result = result && responseMessage.isSuccess();
        }
        return result;
    }

    @Scheduled(cron = "${cron.getData}")
    public void getData() throws InterruptedException, ParserConfigurationException, SAXException, ParseException, IOException {
        aGPSService.getData();
//        aGPSService.givePoint();
    }

    @Scheduled(cron = "${cron.collectData}")
    void CollectData () throws InterruptedException {
        storeGPSDataService.collectData();
    }

    @RequestMapping(value = "/showLast", method = RequestMethod.GET)
    public ResponseMessage showLast(@RequestParam(value="location") String json) throws IOException {
        log.info(json);
        ResponseMessage responseMessage;

        // Пытаемся скушать полученный json
        try {
            ObjectMapper mapper = new ObjectMapper();
            Point dto = mapper.readValue(json, Point.class);
            responseMessage = new ResponseMessage("ok", true);
        } catch (Throwable t) {
            responseMessage = new ResponseMessage("fail", false);
        }

        log.info(responseMessage.message);
        return responseMessage;
    }

    @RequestMapping(value = "/coords", method = RequestMethod.GET)
    public ResponseMessage setCoords(@RequestParam(value="location") String location){
        log.info(location);
        ResponseMessage responseMessage;
        if (location.split(",").length == 2) {
            responseMessage = new ResponseMessage("ok", true);
        } else {
            responseMessage = new ResponseMessage("fail", false);
        }

        return responseMessage;
    }

//    @RequestMapping(value = "/getPoint")
//    public DTO.Point getPoint(){
//        DTO.Point point = restTemplate.getForObject(
//                "http://services.groupkt.com/country/get/iso2code/RU", DTO.Point.class);
//        return point;
//    }
}