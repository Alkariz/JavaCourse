package tracker.services;

import dto.Point;
import com.fasterxml.jackson.core.JsonProcessingException;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class PushMessagesServiceTest extends TestCase {

    @InjectMocks
    PushMessagesService mockedPushMessagesService;

    @Test
    public void testPutPoint() throws ParseException, JsonProcessingException {
        Point point = new Point();
        point.setLat(42.5);
        point.setLon(56.33);
        String s = "2010-08-21T03:23:45.4Z";
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        Date docDate= format.parse(s);
        point.setTime(docDate.getTime());

        int count = mockedPushMessagesService.putPoint(point);
        assertTrue(count>0);
    }

    @Test
    public void testPointsReadyCount() throws ParseException {
        Point point = new Point();
        point.setLat(42.5);
        point.setLon(56.33);
        String s = "2010-08-21T03:23:45.4Z";
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        Date docDate= format.parse(s);
        point.setTime(docDate.getTime());
        mockedPushMessagesService.putPoint(point);
        int result = mockedPushMessagesService.pointsReadyCount();
        assertTrue(result>0);
    }

    @Test
    public void testGetLast() {
        Point point = mockedPushMessagesService.getLast();
        assertNotNull(point);
    }


}