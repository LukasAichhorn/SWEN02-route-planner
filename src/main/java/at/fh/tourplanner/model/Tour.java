package at.fh.tourplanner.model;
import java.util.UUID;
public class Tour {
    private UUID   uuid;
    private String name;
    private String start;
    private String destination;
    private String description;

    private TransportType transportType;


    public Tour(String name, String start, String destination, String description) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.start = start;
        this.destination = destination;
        this.description = description;
    }
    public Tour(UUID uuid,String name, String start, String destination, String description) {
        this.uuid = uuid;
        this.name = name;
        this.start = start;
        this.destination = destination;
        this.description = description;
    }

    @Override
    public String toString() {
        return "'\n'Tour{"+ '\n' +
                "   uuid=" +uuid + '\n' +
                "   name='" + name + '\n' +
                "   start='" + start + '\n' +
                "   destination='" + destination + '\n' +
                "   description='" + description + '\n' +
                '}';
    }
    public void setUUID(UUID uuid) {this.uuid = uuid;}
    public void setName(String name) {
        this.name = name;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public UUID getUUID() {return uuid;}

    public String getStart() {
        return start;
    }

    public String getDestination() {
        return destination;
    }

    public String getDescription() {
        return description;
    }
}
