package DTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by jdev on 06.03.2017.
 */
public class PointTest {

    private final String autoId = "o567gfd";
    private final String expected = "{\"lat\":56.0,\"lon\":74.0,}";
    private Double lat = 56.0;
    private Double lon = 74.0;

    @Test
    public void toJson() throws Exception {
        Point point = new Point();
        point.setLat(56);
        point.setLon(74);
        String pointJson = point.toJson();
        assertTrue(pointJson.contains("\"lat\":56"));
        System.out.println(pointJson);
    }

    @Test
    public void decodeDto() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Point dto = mapper.readValue(expected, Point.class);
        assertTrue(dto.getLat()==lat);
        assertTrue(dto.getLon()==lon);
    }
}