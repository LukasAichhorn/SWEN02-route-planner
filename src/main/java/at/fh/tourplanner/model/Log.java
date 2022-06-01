package at.fh.tourplanner.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

public class Log {
    private UUID uuid;
    private LocalDate timeStamp;
    private int rating;
    private DifficultyTier difficulty;
    private Duration duration;
    private String comment;

    public Log(UUID uuid, LocalDate timeStamp, int rating, DifficultyTier difficulty, Duration duration, String comment) {
        this.uuid = uuid;
        this.timeStamp = timeStamp;
        this.rating = rating;
        this.difficulty = difficulty;
        this.duration = duration;
        this.comment = comment;
    }

    public Log(LocalDate timeStamp, int rating, DifficultyTier difficulty, Duration duration, String comment) {
        this.uuid = UUID.randomUUID();
        this.timeStamp = timeStamp;
        this.rating = rating;
        this.difficulty = difficulty;
        this.duration = duration;
        this.comment = comment;
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDate getTimeStamp() {
        return timeStamp;
    }

    public int getRating() {
        return rating;
    }

    public DifficultyTier getDifficulty() {
        return difficulty;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getComment() {
        return comment;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setDifficulty(DifficultyTier difficulty) {
        this.difficulty = difficulty;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Log{" +
                "uuid=" + uuid +
                ", timeStamp=" + timeStamp +
                ", rating='" + rating + '\'' +
                ", difficulty=" + difficulty +
                ", duration=" + duration +
                ", comment='" + comment + '\'' +
                '}';
    }
}
