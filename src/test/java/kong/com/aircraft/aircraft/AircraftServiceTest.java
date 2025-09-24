package kong.com.aircraft.aircraft;

import kong.com.aircraft.pilot.Pilot;
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
public class AircraftServiceTest {
    @Mock
    AircraftRepository aircraftRepository;

    @InjectMocks
    AircraftService aircraftService;

    Aircraft doghouse = new Aircraft("doghouse", new Pilot("Tyler", "Pak",28));
    List<Aircraft> flight;

    @BeforeEach
    void setUp(){
        flight = new ArrayList<>(List.of(doghouse));
    }

    @Test
    void shouldSaveAircraft() {
        // Arrange
        when(aircraftRepository.save(doghouse)).thenReturn(doghouse);
        // Act
        Aircraft actualAircraft = aircraftService.saveAircraft(doghouse);
        // Assert
        verify(aircraftRepository, times(1)).save(any(Aircraft.class));
        assertThat(actualAircraft).isEqualTo(doghouse);

    }

    @Test
    void shouldGetAircraft() {
        // arrange
        when(aircraftRepository.findAll()).thenReturn(flight);

        // act
        List<Aircraft> findAircraft = aircraftService.getAircraftArrayList();


        // assert
        verify(aircraftRepository, times(1)).findAll();
        assertThat(findAircraft).isEqualTo(flight);
        }

    @Test
    void shouldGetAircraftByID() {
        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(doghouse));

        Aircraft findAircraft = aircraftService.getAircraftByID(1L);

        verify(aircraftRepository, times(1)).findById(1L);
        assertThat(findAircraft).isEqualTo(doghouse);
    }

    @Test
    void shouldDeleteAircraftById() {
        when(aircraftRepository.deleteAircraftById(1L)).thenReturn(null);
        Aircraft deleteAircraft = aircraftRepository.deleteAircraftById(1L);
        verify(aircraftRepository,times(1)).deleteAircraftById(1L);
        assertThat(deleteAircraft).isNull();
    }


}

