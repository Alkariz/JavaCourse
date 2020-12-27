package tracker.controllers;

import DTO.Point;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
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
@EnableScheduling
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

    //@Scheduled(cron = "${cron.pushData}")
    @RequestMapping(value = "/qwe", method = RequestMethod.GET)
    public Point takeThis() throws JsonProcessingException {
        Point point = pushMessagesService.getLast();
        restTemplate.postForObject("http://localhost:8080/takeThis", point, Point.class);
        log.info(point.toJson());
        return point;
    }

    //@Scheduled(cron = "${cron.getData}")
    public void getData() throws InterruptedException, ParserConfigurationException, SAXException, ParseException, IOException {
        aGPSService.givePoint();
    }

    //@Scheduled(cron = "${cron.collectData}")
    void CollectData () throws InterruptedException {
        storeGPSDataService.collectData();
    }

    @RequestMapping(value = "/showLast", method = RequestMethod.GET)
    public Response showLast(@RequestParam(value="location") String json) throws IOException {
        log.info(json);
        Response response;

        // Пытаемся скушать полученный json
        try {
            ObjectMapper mapper = new ObjectMapper();
            Point dto = mapper.readValue(json, Point.class);
            response = new Response("ok", true);
        } catch (Throwable t) {
            response = new Response("fail", false);
        }

        log.info(response.message);
        return response;
    }

    @RequestMapping(value = "/coords", method = RequestMethod.GET)
    public Response setCoords(@RequestParam(value="location") String location){
        log.info(location);
        Response response;
        if (location.split(",").length == 2) {
            response = new Response("ok", true);
        } else {
            response = new Response("fail", false);
        }

        return response;
    }

    @RequestMapping(value = "/getPoint")
    public DTO.Point getPoint(){
        DTO.Point point = restTemplate.getForObject(
                "http://services.groupkt.com/country/get/iso2code/RU", DTO.Point.class);
        return point;
    }
}

/*2.1.3 Используйте компонент restTemplate в сервисе отправки координат для выполнения POST запроса,
        в теле которого будут передаваться координаты по мере их поступления от сервиса GPS.
        Для выполнения POST-запроса подберите соответствующий метод в классе RestTemplate.*/