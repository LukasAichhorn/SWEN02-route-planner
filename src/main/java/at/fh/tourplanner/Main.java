package at.fh.tourplanner;

import at.fh.tourplanner.repositories.MapAPI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));
        fxmlLoader.setControllerFactory(controller -> ControllerFactory.getInstance().create(controller));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Tour Planner");
        stage.setScene(scene);
        stage.show();
        MapAPI.getInstance().requestStaticMap("Vienna","Graz");
    }

    public static void main(String[] args) {
        launch();
    }
}