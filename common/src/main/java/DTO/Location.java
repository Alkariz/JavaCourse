package DTO;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Location {
    Point point;

    public static void meth() {

    }

    public double getLat() {
        return point.getLat();
    }

    public void setLat(double lat) {
        point.setLat(lat);
    }

    public double getLon() {
        return point.getLon();
    }

    public void setLon(double lon) {
        point.setLon(lon);
    }

    public long getTime() {
        return point.getTime();
    }

    public void setTime(long time) {
        point.setTime(time);
    }

    public String toJson() throws JsonProcessingException {
        return point.toJson();
    }
}
