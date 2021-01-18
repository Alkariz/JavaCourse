package tracker.controllers;

import DTO.Point;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import org.junit.jupiter.api.Test;

@RunWith(MockitoJUnitRunner.class)
public class TrackerRestTemplateIntegrationTest extends TestCase {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    TrackerRestTemplate mockedTrackerRestTemplate;

    MockMvc mockMvc;
    MockMvc mockMvcServer;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(mockedTrackerRestTemplate).build();
    }

    @Test
    public void testTakeThisIntegration() throws Exception {
        Point point = new Point();
        point.setLat(42.5);
        point.setLon(56.33);
        String s = "2010-08-21T03:23:45.4Z";
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        Date docDate= format.parse(s);
        point.setTime(docDate.getTime());
        String pointS = point.toString();

        mockMvc.perform(post("http://localhost:8081/takeThis")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(pointS));
    }
}