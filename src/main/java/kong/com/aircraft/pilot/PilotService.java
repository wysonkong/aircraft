package kong.com.aircraft.pilot;

import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class PilotService {
    private final PilotRepository pilotRepository;

    public PilotService(PilotRepository pilotRepository){
        this.pilotRepository = pilotRepository;
    }

    public Pilot savePilot(Pilot pilot) {
        return this.pilotRepository.save(pilot);
    }


    public List<Pilot> getPilots() {
        return (List<Pilot>) pilotRepository.findAll();
    }

    public Pilot getPilotById(Long id) {
        return pilotRepository.findById(id).get();
    }

    public Pilot deletePilotById(Long id) {
        return pilotRepository.deletePilotById(id);
    }
}
