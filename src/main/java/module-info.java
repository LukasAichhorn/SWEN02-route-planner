module at.fh.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires retrofit2;
    requires com.fasterxml.jackson.annotation;
    requires retrofit2.converter.jackson;
    requires okhttp3;
    requires java.desktop;
    requires java.sql;
    requires lombok;
    requires itextpdf;
    requires kernel;
    requires layout;
    requires io;
    requires org.apache.logging.log4j;

    opens at.fh.tourplanner.DataAccessLayer;
    exports at.fh.tourplanner.DataAccessLayer.listener;
    opens at.fh.tourplanner.DataAccessLayer.listener;
    exports at.fh.tourplanner.controller;
    opens at.fh.tourplanner.controller to javafx.fxml;
    exports at.fh.tourplanner.controller.Tour;
    opens at.fh.tourplanner.controller.Tour to javafx.fxml;
    exports at.fh.tourplanner.controller.Log;
    opens at.fh.tourplanner.controller.Log to javafx.fxml;
    exports at.fh.tourplanner.model;
    opens at.fh.tourplanner.model to javafx.base;
    exports at.fh.tourplanner.DataAccessLayer.mapAPI;
    opens at.fh.tourplanner.DataAccessLayer.mapAPI;
    exports at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit;
    opens at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit;
    exports at.fh.tourplanner;
    opens at.fh.tourplanner;

}