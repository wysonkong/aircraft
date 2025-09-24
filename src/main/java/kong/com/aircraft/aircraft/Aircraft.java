package kong.com.aircraft.aircraft;

import jakarta.persistence.*;
import kong.com.aircraft.pilot.Pilot;
import org.springframework.http.HttpHeaders;

@Entity
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String airframe;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pilot_id")


    private Pilot pilot;

    public Aircraft(){
    }

    public Aircraft(String airframe, Pilot pilot) {
        this.airframe = airframe;
        this.pilot = pilot;
    }

    public String getAirframe() {
        return airframe;
    }

    public void setAirframe(String airframe) {
        this.airframe = airframe;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
