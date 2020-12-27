package controllers;

import DTO.Point;
import main.ServerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class PointReciever {

    private final AtomicLong counter = new AtomicLong();
    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);
    private static final BlockingDeque<Point> allPointsQueue =  new LinkedBlockingDeque<>(100);

    @Autowired
    RestTemplate restTemplate;

    private void saveToFile(Point point) throws IOException {
        FileWriter fr = new FileWriter("/Points.txt");
        String pointS = point.toJson();
        fr.write(pointS);
        fr.write(System.lineSeparator());
        log.info(pointS);
        allPointsQueue.add(point);
    }

    @RequestMapping("/firstStart")
    public String firstStart() throws FileNotFoundException {
        Formatter f = new Formatter("Points.txt");
        f.format("");
        return "done";
    }

    @RequestMapping(value = "takeThis", method = RequestMethod.POST)
    public Response takeThis(Point point) {
        Response response = new Response("success", true);
        try {
            saveToFile(point);
        }
        catch (IOException e) {
            response = new Response("failed", false);
        }
        return response;
    }

    @RequestMapping("/getPoint")
    public Response getPoint() throws IOException {
        Response response = new Response("success", true);
        try {
            Point point = restTemplate.getForObject("http://localhost:8080/takeThis", Point.class);
            saveToFile(point);
        }
        catch (IOException e) {
            response = new Response("failed", false);
        }
        return response;

    }



}
