package com.hevlar.springdatanestedentitydemo.controllers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hevlar.springdatanestedentitydemo.models.Father;
import com.hevlar.springdatanestedentitydemo.models.Son;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FatherControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void create_willFail() throws Exception {
        /**
         * We might expect the sons to have the relationships with the father,
         * and we even see that from the results of the post,
         * but if we try to get, we realized that the sons doesn't have the father field populated.
         * We need to manually set the father field for each son.
         */
        Son son1 = Son.builder().name("son 1").build();
        Son son2 = Son.builder().name("son 2").build();
        Father father = Father.builder().name("father").sonList(List.of(son1, son2)).build();

        MvcResult insertResult = mvc.perform(post("/fathers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(father)))
                .andExpect(status().isCreated())
                .andReturn();
        String insertResultJson = insertResult.getResponse().getContentAsString();
        Father insertedFather = objectMapper.readValue(insertResultJson, Father.class);
        assertThat(insertedFather.getSonList().size(), is(2));

        MvcResult getResult = mvc.perform(get("/fathers/" + insertedFather.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(father)))
                .andExpect(status().isOk())
                .andReturn();
        String getResultJson = getResult.getResponse().getContentAsString();
        Father gotFather = objectMapper.readValue(getResultJson, Father.class);
        assertThat(gotFather.getSonList().size(), is(0));
    }

    @Test
    void create_willOverflow(){
        /**
         * However, even if we set the relationship on both sides,
         * we still get error as the objectMapper will recursively map the
         * father and son relationship indefinitely.
         * Plus, it is not intuitive to put the father field in the son, when
         * we already put the sons field in the father's json.
         */
        Son son1 = Son.builder().name("son 1").build();
        Son son2 = Son.builder().name("son 2").build();
        Father father = Father.builder().name("father").sonList(List.of(son1, son2)).build();
        son1.setFather(father);
        son2.setFather(father);

        assertThrows(JsonMappingException.class, () -> objectMapper.writeValueAsString(father));
    }

}
