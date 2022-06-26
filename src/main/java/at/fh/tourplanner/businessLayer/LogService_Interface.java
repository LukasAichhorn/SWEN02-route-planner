package at.fh.tourplanner.businessLayer;

import at.fh.tourplanner.model.Log;

import java.util.List;
import java.util.UUID;

public interface LogService_Interface {
    public List<Log> getLogForTourFromDatabase(UUID tourID);

    void addNewLogToDatabase(Log newLog);
    void updateLogInDatabase(Log newLog);
    void deleteLogInDatabase(UUID logID);

}
