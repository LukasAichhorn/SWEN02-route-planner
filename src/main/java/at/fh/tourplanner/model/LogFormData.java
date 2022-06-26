package at.fh.tourplanner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class LogFormData {
    private LocalDate timeStamp;
    private int rating;
    private DifficultyTier difficulty;
    private int duration;
    private String comment;

}
