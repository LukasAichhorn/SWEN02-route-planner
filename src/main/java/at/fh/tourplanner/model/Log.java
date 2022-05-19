package at.fh.tourplanner.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

public class Log {
    private UUID uuid;
    private LocalDate timeStamp;
    private String rating;
    private DifficultyTier difficulty;
    private Duration duration;
    private String comment;


}
