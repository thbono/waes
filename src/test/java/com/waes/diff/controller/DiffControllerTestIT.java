package com.waes.diff.controller;

import com.waes.diff.DiffApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DiffApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class DiffControllerTestIT {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getNotFoundDiff() throws Exception {
        mvc.perform(get("/v1/diff/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void postWithInvalidContent() throws Exception {
        mvc.perform(post("/v1/diff/2/left").contentType(MediaType.TEXT_PLAIN).content("string"))
                .andExpect(status().isUnprocessableEntity());
        mvc.perform(post("/v1/diff/2/right").contentType(MediaType.TEXT_PLAIN).content("string"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void compare() throws Exception {
        mvc.perform(post("/v1/diff/3/left").contentType(MediaType.TEXT_PLAIN).content("eyJmaXJzdE5hbWUiOiAiVGlhZ28iLCAibGFzdE5hbWUiOiAiQm9ubyJ9"))
                .andExpect(status().isNoContent());
        mvc.perform(post("/v1/diff/3/right").contentType(MediaType.TEXT_PLAIN).content("eyJmaXJzdE5hbWUiOiAiVGlhZ28iLCAibGFzdE5hbWUiOiAiQm9ubyJ9"))
                .andExpect(status().isNoContent());

        mvc.perform(get("/v1/diff/3").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.equals", is(true)));
    }

}