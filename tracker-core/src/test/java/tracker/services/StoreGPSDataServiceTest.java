package tracker.services;

import DTO.Point;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StoreGPSDataServiceTest extends TestCase {

    @Mock
    GPSService currentGPSService;

    @Mock
    PushMessagesService currentPushMessagesService;

    @InjectMocks
    StoreGPSDataService mockedStoreGPSDataService;

    @Test
    public void testCollectData() throws IOException, ParseException {
        Point point = new Point();
        point.setLat(42.5);
        point.setLon(56.33);
        String s = "2010-08-21T03:23:45.4Z";
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        Date docDate= format.parse(s);
        point.setTime(docDate.getTime());

        when(currentGPSService.pointsCount()).thenReturn(1);
        when(currentGPSService.givePoint()).thenReturn(point);
        when(currentPushMessagesService.putPoint(point)).thenReturn(1);
        boolean result = mockedStoreGPSDataService.collectData();
        assertTrue(result);
    }
}