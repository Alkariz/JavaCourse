package dto;

import org.w3c.dom.Node;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Point implements Serializable {

    private double lat;
    private double lon;
    private long  time;
    private double ele; // Высота

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getEle() {
        return ele;
    }

    public void setEle(double ele) {
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
        return "{" +
                "lat: " + lat +
                ", lon: " + lon +
                ", ele: " + ele +
                ", time: " + time +
                '}';
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(Point.class))
        {
            return false;
        }
        return (this.hashCode() == obj.hashCode());
    }
}
