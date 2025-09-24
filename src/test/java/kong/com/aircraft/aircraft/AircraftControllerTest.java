package kong.com.aircraft.aircraft;

import com.fasterxml.jackson.databind.ObjectMapper;
import kong.com.aircraft.pilot.Pilot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AircraftController.class)
@AutoConfigureMockMvc
public class AircraftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    AircraftService aircraftService;
    Aircraft doghouse = new Aircraft("Doghouse", new Pilot("Lewis", "Allen", 28));
    Aircraft boeing = new Aircraft("F16", new Pilot("Colby", "Swann", 26));
    ArrayList<Aircraft> aircrafts = new ArrayList<>();


    @Test
    void shouldCreateAircraft() throws Exception {
        Mockito.when(aircraftService.saveAircraft(any(Aircraft.class))).thenReturn(doghouse);
        String doghouseJson = objectMapper.writeValueAsString(doghouse);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/aircraft")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(doghouseJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.airframe").value("Doghouse"));

        Mockito.verify(aircraftService).saveAircraft(any(Aircraft.class));
    }

    @Test
    void shouldGetAircraftArrayList() throws Exception {
        aircrafts.add(doghouse);
        aircrafts.add(boeing);

        Mockito.when(aircraftService.getAircraftArrayList()).thenReturn(aircrafts);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/aircraft"))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].airframe").value("Doghouse"))
//                .andExpect(jsonPath("$[0].pilot").value("Snoopy"))
//                .andExpect(jsonPath("$[1].airframe").value("F16"))
//                .andExpect(jsonPath("$[1].pilot").value("Maverick"));
                .andExpect(jsonPath("$.*").isArray());

        Mockito.verify(aircraftService).getAircraftArrayList();
    }

    @Test
    void shouldGetAircraftById() throws Exception {
        aircrafts.add(doghouse);
        aircrafts.add(boeing);

        Mockito.when(aircraftService.getAircraftByID(2L)).thenReturn(boeing);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/aircraft/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airframe").value("F16"));

        Mockito.verify(aircraftService).getAircraftByID(2L);
    }


    @Test
    void shouldUpdateAircraft() throws Exception {
        aircrafts.add(doghouse);
        aircrafts.add(boeing);

        Aircraft updated = new Aircraft("UH-60", new Pilot("Lewis", "Allen", 28));

        Mockito.when(aircraftService.updateAirframe("UH-60", 2L)).thenReturn(updated);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/aircraft/2")
                        .param("newAirframe", "UH-60"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.airframe").value("UH-60"));
    }

    @Test
    void shouldFindAircraftsByPilot() throws Exception {
        aircrafts.add(doghouse);
        aircrafts.add(boeing);

        Mockito.when(aircraftService.getAircraftsByPilot(2L)).thenReturn(aircrafts);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/aircraft/pilot/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        Mockito.verify(aircraftService).getAircraftsByPilot(2L);

    }
}
//    @Test
//    void shouldDeleteAircraftById() throws Exception {
//        aircrafts.add(doghouse);
//        aircrafts.add(boeing);
//
//        Mockito.doNothing().when((aircraftService.deleteAircraftById(2L)));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/api/aircraft/pilot/2"))
//                .andExpect(status().isNoContent());
//        Mockito.verify(aircraftService).deleteAircraftById(2L);
//
//    }



