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
public class ServerRestTemplate {

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
        log.info("Добавлена точка" + pointS);
        allPointsQueue.add(point);
    }

    @RequestMapping("/firstStart")
    public String firstStart() throws FileNotFoundException {
        Formatter f = new Formatter("Points.txt");
        f.format("");
        return "done";
    }

    @RequestMapping(value = "takeResp", method = RequestMethod.GET)
    public ResponseMessage takeResp() {
        return new ResponseMessage("success", true);
    }

    @RequestMapping(value = "takeThis", method = RequestMethod.POST)
    public ResponseMessage takeThis(Point point) {
        ResponseMessage responseMessage = new ResponseMessage("success", true);
        try {
//            Point point = restTemplate.getForObject("http://localhost:8083/getPoint", Point.class);
            saveToFile(point);
        }
        catch (IOException e) {
            responseMessage = new ResponseMessage("failed", false);
        }
        return responseMessage;
    }
//    public Response takeThis(String pointString) {
//        Response response = new Response("success", true);
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            Point dto = mapper.readValue(pointString, Point.class);
//            saveToFile(dto);
//        }
//        catch (IOException e) {
//            response = new Response("failed", false);
//        }
//        return response;
//    }
//    public Point takeThis(Point point) {
//        try {
//            saveToFile(point);
//        }
//        catch (IOException e) {
//            return null;
//        }
//        return point;
//    }


}
