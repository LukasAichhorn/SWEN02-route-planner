package at.fh.tourplanner.DataAccessLayer;

import at.fh.tourplanner.DataAccessLayer.DAO;
import at.fh.tourplanner.DataAccessLayer.listener.DbCreateEvent;
import at.fh.tourplanner.model.DifficultyTier;
import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;
import javafx.geometry.Pos;

import java.sql.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostgresDAO implements DAO {
    private final String pw = "admin";
    private final String user = "postgres";
    private final String dbUrl = "jdbc:postgresql://localhost:5432/TourPlannerDB";
    private static final PostgresDAO instance = new PostgresDAO();


    private PostgresDAO() {
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(dbUrl, user, pw);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
            System.out.println("return value from DATABASE LOGs");
            System.out.println(temp);
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
            if(row ==1) System.out.println("created tour entry in DB");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
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
            if(row ==1) System.out.println("updated tour entry in DB");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
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
            if (row == 1) System.out.println("deleted tour entry in DB");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addLog(Log log) {
        String SQL = "INSERT INTO logs (local_date,rating,difficulty_tier," +
                "duration, description,linked_tour ) VALUES (?,?,?,?," +
                "?,?)";
        try (Connection conn = getConnection(); PreparedStatement preparedStatement =
                conn.prepareStatement(SQL)) {
            preparedStatement.setTimestamp(1,
                    Timestamp.valueOf(log.getTimeStamp().atStartOfDay()));
            preparedStatement.setInt(2,log.getRating());
            preparedStatement.setString(3, log.getDifficulty().toString());
            preparedStatement.setDouble(4, log.getDuration());
            preparedStatement.setString(5, log.getComment());
            preparedStatement.setInt(6, log.getTourID());

            int row = preparedStatement.executeUpdate();
            if(row ==1) System.out.println("created tour entry in DB");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateLog(Log log) {

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
                    Timestamp.valueOf(log.getTimeStamp().atStartOfDay()));
            preparedStatement.setInt(2,log.getRating());
            preparedStatement.setString(3, log.getDifficulty().toString());
            preparedStatement.setDouble(4, log.getDuration());
            preparedStatement.setString(5, log.getComment());
            preparedStatement.setInt(6, log.getId());

            int row = preparedStatement.executeUpdate();
            if(row ==1) System.out.println("updated log entry in DB");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
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
            if (row == 1) System.out.println("deleted log entry in DB");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
