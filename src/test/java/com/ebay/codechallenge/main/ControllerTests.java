package com.ebay.codechallenge.main;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Sampling of Spring MockMvc tests to demonstrate how I would go about testing the controllers
 * get a correct status code (200 OK) for a get, and the error message for a bad POST,
 * should this be a production application.
 * Did not test the POST, as it returns 200 OK even in the case of an error, as I did not
 * have time to write the custom exceptions to exercise this, but would have handled in a
 * @ControllerAdvice class and written some custom runtime exceptions.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllSellers() throws Exception {
        this.mockMvc.perform(get("/sellers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOneSeller() throws Exception {
        this.mockMvc.perform(get("/sellers")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateSeller() throws Exception {
        JSONObject payloadObj = new JSONObject();
        payloadObj.put("firstName", "MVC");
        payloadObj.put("lastName", "Test");
        payloadObj.put("enrolled", "false");
        this.mockMvc.perform(post("/sellers")
                .content(payloadObj.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateSellerWithoutFirstNameThrowsError() throws Exception {
        JSONObject payloadObj = new JSONObject();
        payloadObj.put("lastName", "Test");
        payloadObj.put("enrolled", "false");
        MvcResult result = this.mockMvc.perform(post("/sellers")
                .content(payloadObj.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        JSONObject returnObj = new JSONObject(result.getResponse().getContentAsString());

        // Verify error message came through.
        String expected = "firstName is a required field";
        String actual = returnObj.getJSONArray("errorList").getJSONObject(0).get("error").toString();
        Assert.assertEquals(expected, actual);
    }
}
