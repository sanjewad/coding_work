package com.test.demo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Base64;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CounterControllerTest {
    private HttpHeaders authHeaders;
    @Autowired
    private TestRestTemplate template;

    @Before
    public void init() {
        authHeaders = new HttpHeaders();
        String token = new String(Base64.getEncoder().encode(
                ("optus" + ":" + "candidates").getBytes()));
        authHeaders.set("Authorization", "Basic " + token);
    }

    @Test
    public void searchValidText() {
        String requestJson = "{\"searchText\":[\"Duis\", \"Sed\", \"Donec\", \"Augue\", \"Pellentesque\", \"123\"]}";
        authHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<JsonNode> result = template.exchange("/counter-api/search", HttpMethod.POST, new HttpEntity<>(requestJson, authHeaders), JsonNode.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        JsonNode jsonNodeRes = result.getBody().get("counts");
        assertEquals(11, jsonNodeRes.get(0).get("Duis").intValue());
        assertEquals(16, jsonNodeRes.get(1).get("Sed").intValue());
        assertEquals(8, jsonNodeRes.get(2).get("Donec").intValue());
        assertEquals(7, jsonNodeRes.get(3).get("Augue").intValue());
        assertEquals(6, jsonNodeRes.get(4).get("Pellentesque").intValue());
        assertEquals(0, jsonNodeRes.get(5).get("123").intValue());
    }

    @Test
    public void searchValidTextPartOfInput() {
        String requestJson = "{\"searchText\":[\"Duis\", \"Pellentesque\", \"123\"]}";
        authHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<JsonNode> result = template.exchange("/counter-api/search", HttpMethod.POST, new HttpEntity<>(requestJson, authHeaders), JsonNode.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        JsonNode jsonNodeRes = result.getBody().get("counts");
        assertEquals(11, jsonNodeRes.get(0).get("Duis").intValue());
        assertEquals(6, jsonNodeRes.get(1).get("Pellentesque").intValue());
        assertEquals(0, jsonNodeRes.get(2).get("123").intValue());
    }

    @Test
    public void searchEmptyText() {
        String requestJson = "{\"searchText\":[]}";
        authHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<JsonNode> result = template.exchange("/counter-api/search", HttpMethod.POST, new HttpEntity<>(requestJson, authHeaders), JsonNode.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("\"Bad Request\"", result.getBody().get("message").toString());
    }

    @Test
    public void displayTop5TextCount() {
        String expected_top_5 = "text1|100\n" +
                "text2|91\n" +
                "text3|80\n" +
                "text4|70\n" +
                "text5|60\n";
        ResponseEntity<String> result = template.exchange("/counter-api/top/5", HttpMethod.GET, new HttpEntity<>(null, authHeaders), String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expected_top_5, result.getBody());
    }

    @Test
    public void displayTop10TextCount() {
        String expected_top_10 = "text1|100\n" +
                "text2|91\n" +
                "text3|80\n" +
                "text4|70\n" +
                "text5|60\n" +
                "text6|58\n" +
                "text7|56\n" +
                "text8|54\n" +
                "text9|51\n" +
                "text10|48";
        ResponseEntity<String> result = template.exchange("/counter-api/top/10", HttpMethod.GET, new HttpEntity<>(null, authHeaders), String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expected_top_10, result.getBody().trim());
    }

    @Test
    public void displayTop15TextCount() {
        String expected_top_15 = "text1|100\n" +
                "text2|91\n" +
                "text3|80\n" +
                "text4|70\n" +
                "text5|60\n" +
                "text6|58\n" +
                "text7|56\n" +
                "text8|54\n" +
                "text9|51\n" +
                "text10|48\n" +
                "text11|45\n" +
                "text12|42\n" +
                "text13|40\n" +
                "text14|39\n" +
                "text15|37";
        ResponseEntity<String> result = template.exchange("/counter-api/top/15", HttpMethod.GET, new HttpEntity<>(null, authHeaders), String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expected_top_15, result.getBody().trim());
    }

    @Test
    public void displayTop20TextCount() {
        String expected_top_20 = "text1|100\n" +
                "text2|91\n" +
                "text3|80\n" +
                "text4|70\n" +
                "text5|60\n" +
                "text6|58\n" +
                "text7|56\n" +
                "text8|54\n" +
                "text9|51\n" +
                "text10|48\n" +
                "text11|45\n" +
                "text12|42\n" +
                "text13|40\n" +
                "text14|39\n" +
                "text15|37\n" +
                "text16|34\n" +
                "text17|32\n" +
                "text18|31\n" +
                "text19|29\n" +
                "text20|28";
        ResponseEntity<String> result = template.exchange("/counter-api/top/20", HttpMethod.GET, new HttpEntity<>(null, authHeaders), String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expected_top_20, result.getBody().trim());
    }

    @Test
    public void displayInvalidTextCount() {
        String resp = "Invalid Request|";
        ResponseEntity<String> result = template.exchange("/counter-api/top/0", HttpMethod.GET, new HttpEntity<>(null, authHeaders), String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(resp, result.getBody().trim());
    }
}