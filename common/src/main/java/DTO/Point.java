package DTO;

import org.w3c.dom.Node;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Point implements Serializable {

    private Double lat;
    private Double lon;
    private Long time;
    private Double ele; // Высота

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Double getEle() {
        return ele;
    }

    public void setEle(Double ele) {
        this.ele = ele;
    }

    // Получить точку из узла XML
    public void EncodeFromXML(Node elem) throws ParseException {
        this.setLat(Double.valueOf(elem.getAttributes().getNamedItem("lat").getNodeValue()));
        this.setLon(Double.valueOf(elem.getAttributes().getNamedItem("lon").getNodeValue()));
        for (int i = 0; i < elem.getChildNodes().getLength(); i++) {
            Node node = elem.getChildNodes().item(i);
            if (node.getNodeName()=="time") {
                String s = node.getTextContent();

                SimpleDateFormat format = new SimpleDateFormat();
                format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
                Date docDate= format.parse(s);
                this.setTime(docDate.getTime());
            }
            else if (node.getNodeName()=="ele") {
                this.setEle(Double.valueOf(node.getTextContent()));
            }
        }
    }

    @Override
    public String toString() {
        return "Point{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }

}
