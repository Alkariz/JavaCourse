package controllers;

import DTO.Point;
import main.ServerCoreApplication;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {ServerCoreApplication.class})
public class ServerRestTemplateIntergationTest {

    @Autowired
    ServerRestTemplate serverRestTemplate = new ServerRestTemplate();

    MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(serverRestTemplate).build();
    }

    @Test
    public void testTakeResp() {
        ResponseMessage actual = serverRestTemplate.takeResp();
        assertEquals("success", actual.getMessage());
        assertTrue(actual.isSuccess());
    }

    @Test
    public void testTakeRespRS() throws Exception {
        ResponseMessage expected = new ResponseMessage(toString(), true);
        mockMvc.perform(get("http://localhost:8081/takeResp"))
                .andExpect(jsonPath("message").value("success"))
                .andExpect(jsonPath("success").value(true));
    }

    @Test
    public void testTakeThis() throws Exception {
        Point expectedPoint = new Point();
        double lat = 54.105139;
        expectedPoint.setLat(lat);
        double lon = 54.109707;
        expectedPoint.setLon(lon);
        String s = "2010-08-21T03:23:45.4Z";
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        Date docDate= format.parse(s);
        expectedPoint.setTime(docDate.getTime());
        expectedPoint.setEle(319.7);
        String ss = expectedPoint.toString();

        mockMvc.perform(post("http://localhost:8081/takeThis").content(ss)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("message").value("success"))
                .andExpect(jsonPath("success").value(true))
                .andDo(MockMvcResultHandlers.print());
    }

    public void testFirstStart() throws Exception {
        String expected = "done";
        mockMvc.perform(get("http://localhost:8081/firstStart"))
                .andExpect(content().string(expected));
    }

    @Test
    public void testShowPoints() throws Exception {
        Point expectedPoint = new Point();
        expectedPoint.setLat(54.105139);
        expectedPoint.setLon(54.109707);
        String s = "2010-08-21T03:23:45.4Z";
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        Date docDate= format.parse(s);
        expectedPoint.setTime(docDate.getTime());
        expectedPoint.setEle(319.7);

        String expected = "Points count = 1/r/n" + expectedPoint.toString();

        serverRestTemplate.takeThis(expectedPoint);
        mockMvc.perform(get("http://localhost:8081/showPoints"))
                .andExpect(content().string(expected));
    }
}