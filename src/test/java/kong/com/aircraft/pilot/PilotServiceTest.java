package kong.com.aircraft.pilot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PilotServiceTest {
    @Mock
    PilotRepository pilotRepository;

    @InjectMocks
    PilotService pilotService;

    Pilot ace = new Pilot("Tyler", "Pak", 27);
    List<Pilot> pilots;

    @BeforeEach
    void setUp(){
        pilots = new ArrayList<>(List.of(ace));
    }

    @Test
    void shouldSavePilot() {
        when(pilotRepository.save(ace)).thenReturn(ace);
        Pilot actualPilot = pilotService.savePilot(ace);
        verify(pilotRepository, times(1)).save(any(Pilot.class));
        assertThat(actualPilot).isEqualTo(ace);
    }

    @Test
    void shouldGetAllPilot() {
        when(pilotRepository.findAll()).thenReturn(pilots);
        List<Pilot> findPilot = pilotService.getPilots();
        verify(pilotRepository, times(1)).findAll();
        assertThat(findPilot).isEqualTo(pilots);
    }

    @Test
    void shouldGetPilotById() {
        when(pilotRepository.findById(1L)).thenReturn(Optional.of(ace));
        Pilot findPilot = pilotService.getPilotById(1L);
        verify(pilotRepository,times(1)).findById(1L);
        assertThat(findPilot).isEqualTo(ace);
    }

    @Test
    void shouldDeletePilotById() {
        when(pilotRepository.deletePilotById(1L)).thenReturn(null);
        Pilot deletePilot = pilotService.deletePilotById(1L);
        verify(pilotRepository,times(1)).deletePilotById(1L);
        assertThat(deletePilot).isNull();
    }

}
