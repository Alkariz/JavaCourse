package controllers;

import DTO.Point;
import main.ServerCoreApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class ServerRestTemplate {

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(ServerCoreApplication.class);
    private static final List<Point> allPointsQueue =  new ArrayList<>();

    @Scheduled(cron = "${cron.showCount}")
    private void showCount() {
        log.info("Points count: " + allPointsQueue.size());
    }

    private void saveToFile(Point point) throws IOException {
        FileWriter fr = new FileWriter("/Points.txt");
        String pointS = point.toString();
        fr.write(pointS);
        fr.write(System.lineSeparator());
        log.info("Added point " + pointS);
        allPointsQueue.add(point);
    }

    @RequestMapping("/firstStart")
    public String firstStart() throws FileNotFoundException {
        Formatter f = new Formatter("Points.txt");
        f.format("");
        return "done";
    }

    @RequestMapping(value = "showPoints", method = RequestMethod.GET)
    public String showPoints() {
        AtomicReference<String> s = new AtomicReference<>(new String("Points count = " + allPointsQueue.size()));
        allPointsQueue.stream().forEach((point) -> {
                s.set(s + "/r/n" + point.toString());
        });
        return s.get();
    }

    @RequestMapping(value = "takeResp", method = RequestMethod.GET)
    public ResponseMessage takeResp() {
        return new ResponseMessage("success", true);
    }

    public void clearPoints() {
        allPointsQueue.clear();
    }

    @RequestMapping(value = "takeThis", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessage takeThis(@RequestBody Point point) {
        ResponseMessage responseMessage = new ResponseMessage("success", true);
        try {
            saveToFile(point);
        }
        catch (IOException e) {
            responseMessage = new ResponseMessage("failed", false);
        }
        return responseMessage;
    }
}
