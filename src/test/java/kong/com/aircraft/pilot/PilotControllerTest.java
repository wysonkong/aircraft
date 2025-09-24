package kong.com.aircraft.pilot;

import com.fasterxml.jackson.databind.ObjectMapper;
import kong.com.aircraft.aircraft.Aircraft;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PilotController.class)
@AutoConfigureMockMvc
public class PilotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    PilotService pilotService;
    Pilot ace = new Pilot("Wyson", "Kong", 28);
    Pilot wingman = new Pilot("Jake", "Smith", 28);
    List<Pilot> pilots = new ArrayList<>();

    @Test
    void shouldCreatePilot() throws Exception {
        Mockito.when(pilotService.savePilot(any(Pilot.class))).thenReturn(ace);
        String aceJson = objectMapper.writeValueAsString(ace);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/pilot")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aceJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.firstName").value("Wyson"))
                .andExpect(jsonPath("$.lastName").value("Kong"));

        Mockito.verify(pilotService).savePilot(any(Pilot.class));
    }

    @Test
    void shouldGetPilots() throws Exception {
        pilots.add(ace);
        pilots.add(wingman);

        Mockito.when(pilotService.getPilots()).thenReturn(pilots);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/pilot"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray());

        Mockito.verify(pilotService).getPilots();
    }

    @Test
    void shouldGetPilotById() throws Exception {
        pilots.add(ace);
        pilots.add(wingman);

        Mockito.when(pilotService.getPilotById(2L)).thenReturn(wingman);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/pilot/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jake"));

        Mockito.verify(pilotService).getPilotById(2L);
    }


}
