package at.fh.tourplanner.DataAccessLayer;

import at.fh.tourplanner.DataAccessLayer.DAO;
import at.fh.tourplanner.DataAccessLayer.listener.DbCreateEvent;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;
import javafx.geometry.Pos;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostgresDAO implements DAO {
    private final String pw = "admin";
    private final String user = "postgres";
    private final String dbUrl = "jdbc:postgresql://localhost:5432/TourPlannerDB";
    private final List<DbCreateEvent> createListeners = new ArrayList<>();
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
            publishCreatedEvent();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addCreateListener(DbCreateEvent createEventListener) {
        this.createListeners.add(createEventListener);
    }
    private void publishCreatedEvent(){
        for (var listener : createListeners) {
            listener.onCreate();
        }

    }
}
