package by.openbanking.openbankingservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.ws.rs.core.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void givenSaveBasicInfoStep1_whenCorrectInput_thenSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/asdf")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "      \"data\":\"\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}