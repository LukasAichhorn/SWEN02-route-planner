package at.fh.tourplanner.controller;

import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.viewmodels.TourListViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class TourListController implements Initializable {

    private final TourListViewModel viewModel;
    public ListView<Tour> tourList;

    public TourListController(TourListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourList.setItems(viewModel.getTours());
        tourList.setCellFactory(value -> {
            TextFieldListCell<Tour> cell = new TextFieldListCell<Tour>();
            cell.setConverter(new StringConverter<Tour>() {
                @Override
                public String toString(Tour tour) {
                    return tour.getName();
                }

                @Override
                public Tour fromString(String s) {
                    Tour tour = cell.getItem();
                    tour.setName(s);
                    System.out.println(tour);
                    return tour;
                }
            });
            return cell;
        });
        viewModel.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                System.out.println("rebuild listView");
                //tourList.setItems(viewModel.getTours());
            }
        });
    }


}
