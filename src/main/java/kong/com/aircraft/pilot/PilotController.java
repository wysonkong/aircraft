package kong.com.aircraft.pilot;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pilot")
public class PilotController {
    private final PilotService pilotService;

    public PilotController(PilotService pilotService){
        this.pilotService = pilotService;
    }

    @PostMapping("")
    public Pilot createPilot(@RequestBody Pilot pilot) {
        return pilotService.savePilot(pilot);
    }

    @GetMapping("")
    public List<Pilot> getPilots() {
        return pilotService.getPilots();
    }

    @GetMapping("/{id}")
    public Pilot getPilotById(@PathVariable Long id) {
        return pilotService.getPilotById(id);
    }

    @DeleteMapping("/{id}")
    public Pilot deletePilotById(@PathVariable Long id) {
        return pilotService.deletePilotById(id);
    }

}
