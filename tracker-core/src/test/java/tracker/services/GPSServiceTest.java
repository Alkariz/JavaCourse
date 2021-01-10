package tracker.services;

import DTO.Point;
import com.fasterxml.jackson.core.JsonProcessingException;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GPSServiceTest extends TestCase {

    @InjectMocks
    GPSService mockedGPSService;

    @Test
    public void testGetData() throws Exception {
        boolean getData = mockedGPSService.getData();
        assertTrue(getData);
    }

    // Вот тут, честно говоря, можно и вылететь из теста, если мы его (тест) запустили ДО testGetData.
    // А ещё я не уверен, что mockedGPSService в testGetData реально получит точку для haveData
    @Test
    public void testHaveData() {
        boolean haveData = mockedGPSService.haveData();
        assertTrue(haveData);
    }

    @Test
    public void testGivePoint() throws JsonProcessingException {
        Point point = mockedGPSService.givePoint();
        System.out.println("Точка: "+point.toString());
        assertNotNull(point);
    }
}