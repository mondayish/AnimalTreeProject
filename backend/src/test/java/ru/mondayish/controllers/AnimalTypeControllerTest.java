package ru.mondayish.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.mondayish.models.tree.AnimalType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnimalTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static AnimalType animalType;
    private static int animalTypeId;

    @BeforeClass
    public static void setUp() {
        animalType = new AnimalType(0, "test1", 20000);
    }

    @Test
    public void addAnimalType() throws Exception {
        MvcResult result = mockMvc.perform(post("/tree/type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(animalType)))
                .andExpect(status().isOk())
                .andReturn();
        animalTypeId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(get("/tree/root"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.children[?(@.id==" + animalTypeId + ")]").exists())
                .andExpect(jsonPath("$.children[?(@.id==" + animalTypeId + ")].name").value(animalType.getName()))
                .andExpect(jsonPath("$.children[?(@.id==" + animalTypeId + ")].numberOfKinds").value((int) animalType.getNumberOfKinds()));
    }

    @Test
    public void editAnimalType() throws Exception {
        animalType.setId(animalTypeId);
        animalType.setName("testEdit");
        animalType.setNumberOfKinds(30000);

        mockMvc.perform(put("/tree/type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(animalType)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/tree/root"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.children[?(@.id==" + animalTypeId + ")]").exists())
                .andExpect(jsonPath("$.children[?(@.id==" + animalTypeId + ")].name").value(animalType.getName()))
                .andExpect(jsonPath("$.children[?(@.id==" + animalTypeId + ")].numberOfKinds").value((int) animalType.getNumberOfKinds()));
    }

    @Test
    public void removeAnimalType() throws Exception {
        mockMvc.perform(delete("/tree/type/" + animalTypeId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/tree/root"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.children[?(@.id==" + animalTypeId + ")]").doesNotExist());
    }
}
