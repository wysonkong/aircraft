package kong.com.aircraft.aircraft;

import kong.com.aircraft.pilot.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AircraftRepository extends JpaRepository<Aircraft, Long>{

    List<Aircraft> findAircraftsByPilot_Id(Long pilot_id);

    Aircraft deleteAircraftById(Long id);
}

