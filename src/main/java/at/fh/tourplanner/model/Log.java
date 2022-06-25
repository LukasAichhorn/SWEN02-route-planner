package at.fh.tourplanner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Log {
    private int id;
    private LocalDate timeStamp;
    private int rating;
    private DifficultyTier difficulty;
    private Duration duration;
    private String comment;

}
