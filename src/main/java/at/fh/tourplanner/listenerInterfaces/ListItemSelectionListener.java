package at.fh.tourplanner.listenerInterfaces;

import at.fh.tourplanner.model.Tour;

public interface ListItemSelectionListener<T> {
    void fillForm( T t);
}
