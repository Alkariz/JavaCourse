package tracker.controllers;

import DTO.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tracker.services.PushMessagesService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrackerRestTemplateTest {

    @Mock
    PushMessagesService mockPushMessagesService;

    @InjectMocks
    TrackerRestTemplate mockedTrackerRestTemplate;

    @Test
    public void testGetPoint() throws ParseException {
        Point point = new Point();
        point.setLat(42.5);
        point.setLon(56.33);
        String s = "2010-08-21T03:23:45.4Z";
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        Date docDate= format.parse(s);
        point.setTime(docDate.getTime());

        when(mockPushMessagesService.getLast()).thenReturn(point);
        Point gotPoint = mockedTrackerRestTemplate.getPoint();
        assertEquals(point, gotPoint);
    }

}