package at.fh.tourplanner.DataAccessLayer;

import at.fh.tourplanner.DataAccessLayer.DAO;
import at.fh.tourplanner.DataAccessLayer.listener.DbCreateEvent;
import at.fh.tourplanner.configuration.AppConfiguration;
import at.fh.tourplanner.configuration.AppConfigurationLoader;
import at.fh.tourplanner.model.DifficultyTier;
import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;
import javafx.geometry.Pos;
import lombok.extern.log4j.Log4j2;

import java.sql.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
public class PostgresDAO implements DAO {
    private  final String pw;
    private final String user;
    private final String dbUrl;
    private static final PostgresDAO instance = new PostgresDAO(AppConfigurationLoader.getInstance().getAppConfiguration());


    private PostgresDAO(AppConfiguration appConfiguration) {
        this.pw = appConfiguration.getDatasourcePassword();
        this.user = appConfiguration.getDatasourceUsername();
        this.dbUrl = appConfiguration.getDatasourceUrl();
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(dbUrl, user, pw);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PostgresDAO getInstance() {
        return instance;
    }

    @Override
    public List<Tour> getAllTours() {
        List<Tour> temp = new ArrayList<>();
        String SQL = "SELECT * FROM tours";
        try{
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                temp.add(new Tour(
                        rs.getInt("id"),
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("name"),
                        rs.getString("startlocation"),
                        rs.getString("endlocation"),
                        rs.getString("description"),
                        TransportType.valueOf(rs.getString("transporttype")),
                        rs.getString("distance"),
                        rs.getString("estimatedtime"),
                        new ArrayList<>(),
                        rs.getString("imagepath")
                        ));
            }
            return temp;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            log.warn("...");
            return temp;
        }
    }

    @Override
    public List<Log> getAllLogsForTour(int tourID) {
        List<Log> temp = new ArrayList<>();
        String SQL = "SELECT * FROM logs WHERE linked_tour = " + String.valueOf(tourID);
        try{
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                temp.add(new Log(
                        rs.getInt("id"),
                        rs.getTimestamp("local_date").toLocalDateTime().toLocalDate(),
                        rs.getInt("rating"),
                        DifficultyTier.valueOf(rs.getString("difficulty_tier")),
                        rs.getInt("duration"),
                        rs.getString("description"),
                        rs.getInt("linked_tour")
                ));
            }
            return temp;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return temp;
        }
    }

    @Override
    public void addTour(Tour tour) {
        String SQL = "INSERT INTO tours (name,startlocation,endlocation," +
                "description," +
                "transporttype,distance,estimatedtime,imagepath,uuid) VALUES (?,?,?,?," +
                "?,?,?,?,?)";
        try (Connection conn = getConnection(); PreparedStatement preparedStatement =
                conn.prepareStatement(SQL)) {
            preparedStatement.setString(1, tour.getName());
            preparedStatement.setString(2, tour.getStart());
            preparedStatement.setString(3, tour.getDestination());
            preparedStatement.setString(4, tour.getDescription());
            preparedStatement.setString(5, tour.getTransportType().toString());
            preparedStatement.setString(6, tour.getDistance());
            preparedStatement.setString(7, tour.getEstimatedTime());
            preparedStatement.setString(8, tour.getTourImagePath());
            preparedStatement.setString(9, tour.getUUID().toString());

            int row = preparedStatement.executeUpdate();
            if(row ==1) log.info("created tour entry in DB");
        } catch (SQLException e) {
            log.error("SQL State: {}\n{}", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateTour(Tour tour){

        String SQL = "UPDATE tours " +
                "SET name = ?," +
                "startlocation = ?," +
                "endlocation = ?," +
                "description = ?," +
                "transporttype = ?," +
                "distance = ?," +
                "estimatedtime = ?," +
                "imagepath = ? " +
                "WHERE uuid = ?";

        try (Connection conn = getConnection(); PreparedStatement preparedStatement =
                conn.prepareStatement(SQL)) {
            preparedStatement.setString(1, tour.getName());
            preparedStatement.setString(2, tour.getStart());
            preparedStatement.setString(3, tour.getDestination());
            preparedStatement.setString(4, tour.getDescription());
            preparedStatement.setString(5, tour.getTransportType().toString());
            preparedStatement.setString(6, tour.getDistance());
            preparedStatement.setString(7, tour.getEstimatedTime());
            preparedStatement.setString(8, tour.getTourImagePath());
            preparedStatement.setString(9, tour.getUUID().toString());

            int row = preparedStatement.executeUpdate();
            if(row ==1) log.info("updated tour with UUID: {} entry in DB", tour.getUUID());
        } catch (SQLException e) {
            log.error("SQL State: {}\n{}", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteTour(UUID id) {
        String SQL = "DELETE FROM tours " +
                "WHERE uuid = ?";

        try (Connection conn = getConnection(); PreparedStatement preparedStatement =
                conn.prepareStatement(SQL)) {
            preparedStatement.setString(1, id.toString());

            int row = preparedStatement.executeUpdate();
            if (row == 1) log.info("deleted tour with UUID: {} in DB", id);
        } catch (SQLException e) {
            log.error("SQL State: {}\n{}", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addLog(Log newLog) {
        String SQL = "INSERT INTO logs (local_date,rating,difficulty_tier," +
                "duration, description,linked_tour ) VALUES (?,?,?,?," +
                "?,?)";
        try (Connection conn = getConnection(); PreparedStatement preparedStatement =
                conn.prepareStatement(SQL)) {
            preparedStatement.setTimestamp(1,
                    Timestamp.valueOf(newLog.getTimeStamp().atStartOfDay()));
            preparedStatement.setInt(2,newLog.getRating());
            preparedStatement.setString(3, newLog.getDifficulty().toString());
            preparedStatement.setDouble(4, newLog.getDuration());
            preparedStatement.setString(5, newLog.getComment());
            preparedStatement.setInt(6, newLog.getTourID());

            int row = preparedStatement.executeUpdate();
            if(row ==1) log.info("created log in DB for tour with ID:{}", newLog.getTourID());
        } catch (SQLException e) {
            log.error("SQL State: {}\n{}", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateLog(Log updateLog) {

        String SQL = "UPDATE logs " +
                "SET local_date = ?," +
                "rating = ?," +
                "difficulty_tier = ?," +
                "duration = ?," +
                "description = ? " +
                "WHERE id = ?";

        try (Connection conn = getConnection(); PreparedStatement preparedStatement =
                conn.prepareStatement(SQL)) {
            preparedStatement.setTimestamp(1,
                    Timestamp.valueOf(updateLog.getTimeStamp().atStartOfDay()));
            preparedStatement.setInt(2,updateLog.getRating());
            preparedStatement.setString(3, updateLog.getDifficulty().toString());
            preparedStatement.setDouble(4, updateLog.getDuration());
            preparedStatement.setString(5, updateLog.getComment());
            preparedStatement.setInt(6, updateLog.getId());

            int row = preparedStatement.executeUpdate();
            if(row ==1) log.info("updated log in DB with ID:{}", updateLog.getId());
        } catch (SQLException e) {
            log.error("SQL State: {}\n{}", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteLog(int id) {

        String SQL = "DELETE FROM logs " +
                "WHERE id = ?";

        try (Connection conn = getConnection(); PreparedStatement preparedStatement =
                conn.prepareStatement(SQL)) {
            preparedStatement.setInt(1, id);

            int row = preparedStatement.executeUpdate();
            if (row == 1)log.info("deleted log with ID: {} from DB", id);
        } catch (SQLException e) {
            log.error("SQL State: {}\n{}", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
