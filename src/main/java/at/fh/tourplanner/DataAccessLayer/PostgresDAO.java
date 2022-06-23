package at.fh.tourplanner.DataAccessLayer;

import at.fh.tourplanner.DataAccessLayer.DAO;
import at.fh.tourplanner.DataAccessLayer.listener.DbCreateEvent;
import at.fh.tourplanner.model.Tour;
import javafx.geometry.Pos;

import java.sql.*;

import java.util.List;

public class PostgresDAO implements DAO {
    private final String pw = "admin";
    private final String user = "postgres";
    private final String dbUrl ="jdbc:postgresql://localhost:5432/TourPlannerDB";
    private static final PostgresDAO instance = new PostgresDAO();


    private PostgresDAO(){
    }
    private Connection getConnection(){
        try{
            return DriverManager.getConnection(dbUrl,user,pw);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
    public static PostgresDAO getInstance(){return instance;}

    @Override
    public List<Tour> getAllTours() {return null;
//        String SQL = "SELECT * FROM tours";
//        try{
//            Connection conn = getConnection();
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(SQL);
//            //TODO create converter
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
    }

    @Override
    public void addTour(Tour tour) {
        String SQL = "INSERT INTO tours (name,startlocation,endlocation,description," +
                "transporttype,distance,estimatedtime,imagepath) VALUES (?,?,?,?,?,?,?," +
                "?)";
        try(Connection conn = getConnection();PreparedStatement preparedStatement =
                conn.prepareStatement(SQL)){
            preparedStatement.setString(1,tour.getName());
            preparedStatement.setString(2,tour.getStart());
            preparedStatement.setString(3,tour.getDestination());
            preparedStatement.setString(4,tour.getDescription());
            preparedStatement.setString(5,tour.getTransportType().toString());
            preparedStatement.setString(6,tour.getDistance());
            preparedStatement.setString(7,tour.getEstimatedTime());
            preparedStatement.setString(8,tour.getTourImagePath());

            int row = preparedStatement.executeUpdate();

        }
        catch(SQLException e){
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void addCreateListener(DbCreateEvent createEventListener) {

    }
}
