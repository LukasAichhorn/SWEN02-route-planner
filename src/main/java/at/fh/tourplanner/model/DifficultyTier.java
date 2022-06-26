package at.fh.tourplanner.model;

public enum DifficultyTier {
    BEGINNER("Anfänger"),
    Mittel("Mittel"),
    ADVANCED("Fortgeschritten"),
    MASTER("Profi");

    private String type;

    DifficultyTier(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
