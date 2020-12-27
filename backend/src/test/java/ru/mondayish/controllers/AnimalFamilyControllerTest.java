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
import ru.mondayish.models.tree.AnimalClass;
import ru.mondayish.models.tree.AnimalFamily;
import ru.mondayish.models.tree.AnimalSquad;
import ru.mondayish.models.tree.AnimalType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnimalFamilyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static AnimalType animalType;
    private static AnimalClass animalClass;
    private static AnimalSquad animalSquad;
    private static AnimalFamily animalFamily;
    private static int animalTypeId;
    private static int animalClassId;
    private static int animalSquadId;
    private static int animalFamilyId;

    @BeforeClass
    public static void setUp() {
        animalType = new AnimalType(0, "test1", 20000);
        animalClass = new AnimalClass(0, "test2", 5000);
        animalSquad = new AnimalSquad(0, "test3");
        animalFamily = new AnimalFamily(0, "test4");
    }

    @Test
    public void addAnimalFamily() throws Exception {
        MvcResult result = mockMvc.perform(post("/tree/type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(animalType)))
                .andExpect(status().isOk())
                .andReturn();
        animalTypeId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        result = mockMvc.perform(post("/tree/type/" + animalTypeId + "/class")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(animalClass)))
                .andExpect(status().isOk())
                .andReturn();
        animalClassId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        result = mockMvc.perform(post("/tree/class/" + animalClassId + "/squad")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(animalSquad)))
                .andExpect(status().isOk())
                .andReturn();
        animalSquadId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        result = mockMvc.perform(post("/tree/squad/" + animalSquadId + "/family")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(animalFamily)))
                .andExpect(status().isOk())
                .andReturn();
        animalFamilyId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(get("/tree/root"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.children[?(@.id==" + animalTypeId + ")].children[?(@.id==" + animalClassId + ")].children[?(@.id==" + animalSquadId + ")].children[?(@.id==" + animalFamilyId + ")]").exists())
                .andExpect(jsonPath("$.children[?(@.id==" + animalTypeId + ")].children[?(@.id==" + animalClassId + ")].children[?(@.id==" + animalSquadId + ")].children[?(@.id==" + animalFamilyId + ")].name").value(animalFamily.getName()));
    }

    @Test
    public void editAnimalFamily() throws Exception {
        animalFamily.setId(animalFamilyId);
        animalFamily.setName("testEdit");

        mockMvc.perform(put("/tree/squad/" + animalSquadId + "/family")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(animalFamily)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/tree/root"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.children[?(@.id==" + animalTypeId + ")].children[?(@.id==" + animalClassId + ")].children[?(@.id==" + animalSquadId + ")].children[?(@.id==" + animalFamilyId + ")]").exists())
                .andExpect(jsonPath("$.children[?(@.id==" + animalTypeId + ")].children[?(@.id==" + animalClassId + ")].children[?(@.id==" + animalSquadId + ")].children[?(@.id==" + animalFamilyId + ")].name").value(animalFamily.getName()));
    }

    @Test
    public void removeAnimalFamily() throws Exception {
        mockMvc.perform(delete("/tree/squad/" + animalSquadId + "/family/" + animalFamilyId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/tree/root"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.children[?(@.id==" + animalTypeId + ")].children[?(@.id==" + animalClassId + ")].children[?(@.id==" + animalSquadId + ")].children[?(@.id==" + animalFamilyId + ")]").doesNotExist());

    }
}
