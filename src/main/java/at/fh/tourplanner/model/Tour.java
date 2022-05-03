package at.fh.tourplanner.model;


public class Tour {

    private String name;
    private String start;
    private String destination;
    private String description;


    public Tour(String name, String start, String destination, String description) {
        this.name = name;
        this.start = start;
        this.destination = destination;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "name='" + name + '\'' +
                ", start='" + start + '\'' +
                ", destination='" + destination + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

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
