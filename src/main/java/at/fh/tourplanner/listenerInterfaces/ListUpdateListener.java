package at.fh.tourplanner.listenerInterfaces;

import at.fh.tourplanner.model.Tour;

import java.util.List;

public interface ListUpdateListener<T> {
    void handleListUpdate(T t);

}
