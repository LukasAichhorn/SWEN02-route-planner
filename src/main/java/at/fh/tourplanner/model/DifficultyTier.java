package at.fh.tourplanner.model;

public enum DifficultyTier {
    Anfaenger("Anfaenger"),
    Mittel("Mittel"),
    Fortgeschritten("Fortgeschritten"),
    Profi("Profi");

    private String type;

    DifficultyTier(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
