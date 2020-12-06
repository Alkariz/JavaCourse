package tracker.services;

import DTO.Point;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class GPSService {

    // Накапливаемые данные
    private static final BlockingDeque<String> localQueue =  new LinkedBlockingDeque<>(100);

    public boolean haveData () {
        return !localQueue.isEmpty();
    }

    // Локальная переменная для обозначения текущей использованной точки
    private int count;
    // Текущая строка в самом файле-ресурсе
    private int current;

    @Scheduled(cron = "${cron.getData}")
    void getData() throws ParserConfigurationException, IOException, SAXException, ParseException, InterruptedException {

        File fXmlFile = new File(getClass().getResource("/GPSData.xml").getFile());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("trkseg");
        Node parentNode = nList.item(0);
        if (current== parentNode.getChildNodes().getLength()) {
            return;
        }
        /*for (int i = current; i < parentNode.getChildNodes().getLength(); i++) */
        while (true) {
            Node nNode = parentNode.getChildNodes().item(current);
            if (!nNode.getNodeName().equals("trkpt")) {
                current++;
                continue;
            }
            String s = nNode.toString();

            Point point = new Point();
            point.EncodeFromXML(nNode);
            localQueue.put(point.toJson());
            System.out.println("Взяли точку "+count++);
            current++;
            return;
        }
    }

    public String giveData() {
        return localQueue.pollFirst();
    }
}
