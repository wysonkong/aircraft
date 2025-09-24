package kong.com.aircraft.aircraft;

import kong.com.aircraft.pilot.Pilot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class AircraftService {
    private final AircraftRepository aircraftRepository;


    private ArrayList<Aircraft> aircraftArrayList = new ArrayList<Aircraft>();

    public AircraftService(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    public Aircraft saveAircraft(Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    public ArrayList<Aircraft> getAircraftArrayList() {
        return (ArrayList<Aircraft>) aircraftRepository.findAll();
    }

    public Aircraft getAircraftByID(Long id) {
        return aircraftRepository.findById(id).get();
    }

//    public Aircraft getAircraftByPilot(String pilot) {
//        return aircraftRepository.findByPilot(pilot);
//    }

    public Aircraft updateAirframe(String newAirframe, Long id) {
        return aircraftRepository.findById(id)
                .map(existing -> {
                    existing.setAirframe(newAirframe);
                    return aircraftRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Cannot update that ID"));
    }

    public List<Aircraft> getAircraftsByPilot(Long id) {
            return aircraftRepository.findAircraftsByPilot_Id(id);
    }

    public Aircraft deleteAircraftById(Long id) {
        return aircraftRepository.deleteAircraftById(id);
    }
}
