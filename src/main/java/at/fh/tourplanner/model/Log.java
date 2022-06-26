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
    private int duration;
    private String comment;
    private int tourID;

    public Log(LocalDate timeStamp, int rating, DifficultyTier difficulty,
               int duration, String comment,int tourID) {
        this.timeStamp = timeStamp;
        this.rating = rating;
        this.difficulty = difficulty;
        this.duration = duration;
        this.comment = comment;
        this.tourID = tourID;
    }
}
