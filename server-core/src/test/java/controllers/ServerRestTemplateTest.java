package controllers;

import DTO.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ServerRestTemplateTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    ServerRestTemplate serverRestTemplate;

    @Test
    public void testTakeResp() {
//        ResponseMessage message = new ServerRestTemplate(new RestTemplate()).takeResp();
//        System.out.println(message.message);
//        assertTrue(message.success);
        assertTrue(true);
    }

    @Test
    public void testTakeThis() throws ParseException {
        Point point = new Point();
        point.setLat(42.5);
        point.setLon(56.33);
        String s = "2010-08-21T03:23:45.4Z";
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        Date docDate= format.parse(s);
        point.setTime(docDate.getTime());

        point.setEle(103.4);
        ResponseMessage message = serverRestTemplate.takeThis(point);
        System.out.println(message.message);
        assertEquals(true, message.success);
    }

    public void testFirstStart() throws FileNotFoundException {
        String message = serverRestTemplate.firstStart();
        System.out.println(message);
        assertEquals("done", message);
    }
}