package kong.com.aircraft.pilot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PilotControllerIntegrationTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    PilotRepository pilotRepository;

    Pilot snoopy = new Pilot("Snoopy", "Dog", 7);
    Pilot baron = new Pilot("Red Baron","German", 32);

    @Test
    void shouldCreatePilot() throws Exception {
        String pilotJson = mapper.writeValueAsString(snoopy);
        mvc.perform(post("/api/pilot")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pilotJson))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.firstName").value("Snoopy"))
                .andExpect(jsonPath("$.age").value(7));
    }

    @Test
    void shouldGetAllPilots() throws Exception {
        pilotRepository.save(snoopy);
        pilotRepository.save(baron);

        mvc.perform(get("/api/pilot"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].firstName").value("Snoopy"));
    }

    @Test
    void shouldGetPilotById() throws Exception {
        Pilot savedPilot = pilotRepository.save(snoopy);

        mvc.perform(MockMvcRequestBuilders.get("/api/pilot/" + savedPilot.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Snoopy"));

    }

}