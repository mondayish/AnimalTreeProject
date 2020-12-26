package ru.mondayish.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TreeRootControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getRoot() throws Exception {
        mockMvc.perform(get("/tree/root"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.children").exists())
                .andExpect(jsonPath("$.id").value(0));
    }


}
