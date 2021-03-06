package dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
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
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class PointTest {

    @Test
    public void testToString() throws ParseException, JsonProcessingException {
        PointDAO expectedPointDTO = new PointDAO();
        expectedPointDTO.setLat(54.105139);
        expectedPointDTO.setLon(54.109707);
        String s = "2010-08-21T03:23:45.4Z";
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        Date docDate= format.parse(s);
        expectedPointDTO.setTime(docDate.getTime());
        expectedPointDTO.setEle(319.7);

        String pointToString = expectedPointDTO.toString();
        String expected = "{lat: 54.105139, lon: 54.109707, ele: 319.7, time: 1282335825000}";
        assertEquals(expected, pointToString);
    }

    @Test
    public void testEncodeFromXML() throws ParseException, IOException, SAXException, ParserConfigurationException {
        File fXmlFile = new File(getClass().getResource("/GPSData.xml").getFile());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("trkseg");
        Node parentNode = nList.item(0);
        PointDAO actualPointDTO = new PointDAO();
        for (int i = 0; i < parentNode.getChildNodes().getLength(); i++) {
            Node nNode = parentNode.getChildNodes().item(i);
            if (nNode.getNodeName().equals("trkpt")) {
                actualPointDTO.EncodeFromXML(nNode);
                break;
            }
        }

        PointDAO expectedPointDTO = new PointDAO();
        expectedPointDTO.setLat(54.105139);
        expectedPointDTO.setLon(54.109707);
        String s = "2010-08-21T03:23:45.4Z";
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        Date docDate= format.parse(s);
        expectedPointDTO.setTime(docDate.getTime());
        expectedPointDTO.setEle(319.7);

        assertEquals(expectedPointDTO, actualPointDTO);
    }
}