package com.waes.diff.domain.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JsonHelperTest {

    private JsonHelper jsonHelper;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        jsonHelper = new JsonHelper(objectMapper);

    }

    @Test
    public void fromBase64() throws Exception {
        JsonNode jsonNode = jsonHelper.fromBase64("eyJmaXJzdE5hbWUiOiAiVGlhZ28iLCAibGFzdE5hbWUiOiAiQm9ubyJ9");
        String json = objectMapper.writeValueAsString(jsonNode);
        Assert.assertEquals("{\"firstName\":\"Tiago\",\"lastName\":\"Bono\"}", json);
    }

    @Test
    public void compareEquals() {
        JsonNode jsonNode1 = jsonHelper.fromBase64("eyJmaXJzdE5hbWUiOiAiVGlhZ28iLCAibGFzdE5hbWUiOiAiQm9ubyJ9");
        JsonNode jsonNode2 = jsonHelper.fromBase64("eyJmaXJzdE5hbWUiOiAiVGlhZ28iLCAibGFzdE5hbWUiOiAiQm9ubyJ9");
        String result = jsonHelper.compare(jsonNode1, jsonNode2);
        Assert.assertEquals("{\"equals\":true,\"equalsInSize\":true,\"diff\":[]}", result);
    }

    @Test
    public void compareDifferentSize() {
        JsonNode jsonNode1 = jsonHelper.fromBase64("eyJmaXJzdE5hbWUiOiAiVGlhZ28iLCAibGFzdE5hbWUiOiAiQm9ubyJ9");
        JsonNode jsonNode2 = jsonHelper.fromBase64("eyJmaXJzdE5hbWUiOiAiVGlhZ28iLCAibWlkZGxlTmFtZSI6ICJIZW5yaXF1ZSIsICJsYXN0TmFtZSI6ICJCb25vIn0=");
        String result = jsonHelper.compare(jsonNode1, jsonNode2);
        Assert.assertEquals("{\"equals\":false,\"equalsInSize\":false,\"diff\":[]}", result);
    }

    @Test
    public void compare() {
        JsonNode jsonNode1 = jsonHelper.fromBase64("eyJmaXJzdE5hbWUiOiAiVGlhZ28iLCAibGFzdE5hbWUiOiAiQm9ubyJ9");
        JsonNode jsonNode2 = jsonHelper.fromBase64("eyJmaXJzdE5hbWUiOiAiVGlhZ28iLCAibWlkZGxlTmFtZSI6ICJIZW5yaXF1ZSJ9");
        String result = jsonHelper.compare(jsonNode1, jsonNode2);
        Assert.assertEquals("{\"equals\":false,\"equalsInSize\":true,\"diff\":[{\"op\":\"remove\",\"path\":\"/lastName\"},{\"op\":\"add\",\"path\":\"/middleName\",\"value\":\"Henrique\"}]}", result);
    }
}