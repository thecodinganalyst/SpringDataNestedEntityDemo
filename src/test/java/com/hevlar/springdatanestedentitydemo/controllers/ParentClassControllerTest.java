package com.hevlar.springdatanestedentitydemo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hevlar.springdatanestedentitydemo.controllers.dto.ChildClassDto;
import com.hevlar.springdatanestedentitydemo.controllers.dto.ParentClassDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ParentClassControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void update() throws Exception {
        // First create the nested entities
        ChildClassDto child1 = ChildClassDto.builder()
                .name("child1")
                .build();
        ChildClassDto child2 = ChildClassDto.builder()
                .name("child2")
                .build();
        ParentClassDto parent = ParentClassDto.builder()
                .name("Parent")
                .children(List.of(child1, child2))
                .build();

        MvcResult insertResult = mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parent)))
                .andExpect(status().isCreated())
                .andReturn();
        String insertResultJson = insertResult.getResponse().getContentAsString();
        ParentClassDto insertedParent = objectMapper.readValue(insertResultJson, ParentClassDto.class);

        // Then update
        ChildClassDto child1Update = ChildClassDto.builder().name("Updated Child 1").build();
        ChildClassDto child2Update = ChildClassDto.builder().name("Updated Child 2").build();
        ParentClassDto parentUpdate = ParentClassDto.builder()
                .name("Updated Parent")
                .children(List.of(child1Update, child2Update))
                .build();

        MvcResult updateResult = mvc.perform(put("/" + insertedParent.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parentUpdate)))
                .andExpect(status().isOk())
                .andReturn();
        String updateResultJson = updateResult.getResponse().getContentAsString();
        ParentClassDto updatedParent = objectMapper.readValue(updateResultJson, ParentClassDto.class);

        assertThat(updatedParent, is(parentUpdate));

        // Make sure next get still gets the same result
        MvcResult getResult = mvc.perform(get("/" + insertedParent.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parentUpdate)))
                .andExpect(status().isOk())
                .andReturn();
        String getResultJson = getResult.getResponse().getContentAsString();
        ParentClassDto gotParent = objectMapper.readValue(getResultJson, ParentClassDto.class);

        assertThat(gotParent, is(updatedParent));
    }
}
