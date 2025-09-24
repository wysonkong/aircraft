package kong.com.aircraft.aircraft;

import com.fasterxml.jackson.databind.ObjectMapper;
import kong.com.aircraft.pilot.Pilot;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AircraftControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    AircraftRepository aircraftRepository;


    Aircraft savedAircraft = new Aircraft("777", new Pilot("Sam","Less",27));


    @Test
    void shouldCreateAircraft() throws Exception {
        String aircraftJson = mapper.writeValueAsString(savedAircraft);

        MvcResult result = mockMvc.perform(post("/api/aircraft")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aircraftJson)).andReturn();

        String responseType = result.getResponse().getContentType();
        String responseBody = result.getResponse().getContentAsString();
        Aircraft aircraftResult = mapper.readValue(responseBody, Aircraft.class);

        assertEquals(aircraftResult.getAirframe(), savedAircraft.getAirframe());
        assertEquals(responseType.toString(), "application/json");
        assertEquals(aircraftResult.getPilot().getFirstName(), savedAircraft.getPilot().getFirstName());
    }

    @Test
    public void anotherWayToTestShouldCreateAircraft() throws Exception {
        String savedAircraftJson = mapper.writeValueAsString(savedAircraft);

        MvcResult aircraft = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/aircraft")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(savedAircraftJson))
                .andReturn();
        String expectType = aircraft.getRequest().getContentType();
        Aircraft expectedAircraft = mapper.readValue(aircraft.getResponse().getContentAsString(), Aircraft.class);

        assertEquals(expectType, "application/json");
        assertEquals(expectedAircraft.getAirframe(), savedAircraft.getAirframe());
        assertEquals(expectedAircraft.getPilot().getFirstName(), savedAircraft.getPilot().getFirstName());
    }

    @Test
    public void shouldGetAllAircraft() throws Exception {
        aircraftRepository.save(savedAircraft);

        mockMvc.perform(get("/api/aircraft"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].airframe").value("777"))
                .andExpect(jsonPath("$[0].pilot.firstName").value("Sam"));

    }

    @Test
    public void shouldGetAircraftById() throws Exception {
        Aircraft aircraftId = aircraftRepository.save(savedAircraft);

        mockMvc.perform(get("/api/aircraft/" + aircraftId.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airframe").value("777"))
                .andExpect(jsonPath("$.pilot.firstName").value("Sam"))
                .andExpect(jsonPath("$.pilot.age").value(27));
    }



    @Test
    void shouldGetAircraftArrayList() throws Exception {
        mockMvc.perform(get("/api/aircraft"))
                .andExpect(jsonPath("$.*").isArray())
                .andReturn();
    }

}
