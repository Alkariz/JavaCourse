package tracker.services;

import DTO.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final BlockingDeque<Point> localPoints =  new LinkedBlockingDeque<>(100);
    private static final Logger log = LoggerFactory.getLogger(PushMessagesService.class);

    // Локальная переменная для обозначения текущей использованной точки
    private int count;
    // Текущая строка в самом файле-ресурсе
    private int current;

//    @Scheduled(cron = "${cron.getData}")
    public boolean getData() throws ParserConfigurationException, IOException, SAXException, ParseException, InterruptedException {

        File fXmlFile = new File(getClass().getResource("/GPSData.xml").getFile());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("trkseg");
        Node parentNode = nList.item(0);
        if (current== parentNode.getChildNodes().getLength()) {
            return false;
        }
        while (true) {
            Node nNode = parentNode.getChildNodes().item(current);
            if (!nNode.getNodeName().equals("trkpt")) {
                current++;
                continue;
            }
            String s = nNode.toString();

            Point point = new Point();
            point.EncodeFromXML(nNode);
            localPoints.put(point);

            log.info("Взяли точку "+count++);

            current++;
            return true;
        }
    }

    public boolean haveData () {
        return !localPoints.isEmpty();
    }

    public Point givePoint() {
        return localPoints.pollFirst();
    }
}
