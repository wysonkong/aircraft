package kong.com.aircraft.aircraft;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/aircraft")
public class AircraftController {

    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @PostMapping("")
    public Aircraft createAircraft(@RequestBody Aircraft aircraft){
        return aircraftService.saveAircraft(aircraft);
    }

    @GetMapping("")
    public ArrayList<Aircraft> getAllAircraft() {
        return aircraftService.getAircraftArrayList();
    }

    @GetMapping("/{id}")
    public Aircraft getAircraftByID(@PathVariable Long id) {
        return aircraftService.getAircraftByID(id);
    }

//    @GetMapping("/pilot/{pilot}")
//    public Aircraft getAircraftByPilot(@PathVariable String pilot) {
//        return aircraftService.getAircraftByPilot(pilot);
//    }

    @PutMapping("/{id}")
    public Aircraft updateAircraft(@RequestParam String newAirframe, @PathVariable Long id) {
        return aircraftService.updateAirframe(newAirframe, id);
    }

    @GetMapping("/pilot/{id}")
    public List<Aircraft> findAircraftsByPilot_Id(@PathVariable Long id) {
        return aircraftService.getAircraftsByPilot(id);
    }

    @DeleteMapping("/{id}")
    public Aircraft deleteAircraftById(@PathVariable Long id) {
        return aircraftService.deleteAircraftById(id);
    }
}
