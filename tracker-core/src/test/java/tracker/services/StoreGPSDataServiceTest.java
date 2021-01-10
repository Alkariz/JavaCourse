package tracker.services;

import DTO.Point;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StoreGPSDataServiceTest extends TestCase {

    @Mock
    GPSService currentGPSService;

    @InjectMocks
    StoreGPSDataService mockedStoreGPSDataService;

    @Test
    public void testCollectData() throws InterruptedException, IOException, ParseException {
        Point point = new Point();
        point.setLat(42.5);
        point.setLon(56.33);
        String s = "2010-08-21T03:23:45.4Z";
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        Date docDate= format.parse(s);
        point.setTime(docDate.getTime());

        String pointS = point.toString();
        int count = 1;
        when(currentGPSService.haveData()).thenReturn((count--)==0);
        when(currentGPSService.givePoint()).thenReturn(point);
        boolean result = mockedStoreGPSDataService.collectData();
        assertTrue(result);
    }
}