module at.fh.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;



    opens at.fh.tourplanner to javafx.fxml;
    exports at.fh.tourplanner;
    exports at.fh.tourplanner.controller;
    opens at.fh.tourplanner.controller to javafx.fxml;
    exports at.fh.tourplanner.controller.Tour;
    opens at.fh.tourplanner.controller.Tour to javafx.fxml;
    exports at.fh.tourplanner.controller.Log;
    opens at.fh.tourplanner.controller.Log to javafx.fxml;
}