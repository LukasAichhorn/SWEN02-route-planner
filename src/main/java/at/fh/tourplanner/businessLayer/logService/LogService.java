package at.fh.tourplanner.businessLayer.logService;

import at.fh.tourplanner.DataAccessLayer.DAO;
import at.fh.tourplanner.businessLayer.logService.LogService_Interface;
import at.fh.tourplanner.model.Log;

import java.util.List;

public class LogService implements LogService_Interface {
    private final DAO database;

    public LogService(DAO database){
        this.database = database;
    }
    @Override
    public List<Log> getLogForTourFromDatabase(int tourID) {
        return database.getAllLogsForTour(tourID);
    }

    @Override
    public void addNewLogToDatabase(Log newLog) {
        database.addLog(newLog);
    }

    @Override
    public void updateLogInDatabase(Log newLog) {
        database.updateLog(newLog);
    }

    @Override
    public void deleteLogInDatabase(int logID) {
        database.deleteLog(logID);

    }
}
