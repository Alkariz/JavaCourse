package tracker.controllers;

import DTO.Point;
import com.fasterxml.jackson.core.JsonProcessingException;
import controllers.ResponseMessage;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;
import tracker.services.GPSService;
import tracker.services.PushMessagesService;
import tracker.services.StoreGPSDataService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.when;

//import org.junit.jupiter.api.Test;

@RunWith(MockitoJUnitRunner.class)
public class TrackerRestTemplateTest extends TestCase {

    @Mock
    StoreGPSDataService storeGPSDataService;

    @Mock
    GPSService aGPSService;

    @Mock
    PushMessagesService mockPushMessagesService;

    @Mock
    RestTemplate restTemplate;


    @InjectMocks
    TrackerRestTemplate mockedTrackerRestTemplate;

    @Test
    public void testGetData() throws InterruptedException, ParserConfigurationException, SAXException, ParseException, IOException {
        when(aGPSService.getData()).thenReturn(true);
        boolean result = mockedTrackerRestTemplate.getData();
        assertTrue(result);
    }

    @Test
    public void testCollectData() throws JsonProcessingException {
        when(storeGPSDataService.collectData()).thenReturn(true);
        boolean result = mockedTrackerRestTemplate.collectData();
        assertTrue(result);
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

        ResponseMessage responseMessage = new ResponseMessage("success", true);
        when(mockPushMessagesService.pointsReadyCount()).thenReturn(1);
        when(mockPushMessagesService.getLast()).thenReturn(point);
        when(restTemplate.postForObject("http://localhost:8081/takeThis", point, ResponseMessage.class)).thenReturn(responseMessage);
        boolean result = mockedTrackerRestTemplate.takeThis();
        assertTrue(result);
    }

    @Test
    public void testShowPoints() {
        String exected = "Количество точек = 0";
        String actual = mockedTrackerRestTemplate.showPoints();
        assertEquals(exected, actual);
    }
}