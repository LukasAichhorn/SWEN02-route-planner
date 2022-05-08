package at.fh.tourplanner.listenerInterfaces;

import at.fh.tourplanner.model.Tour;

public interface FormActionCreateListener extends FormActionListener{
    void handleCreateAction(Tour formData);
}
