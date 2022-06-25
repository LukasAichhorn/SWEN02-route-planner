package at.fh.tourplanner.controller.Tour;

import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.viewmodels.Tours.TourListViewModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class TourListController implements Initializable {

    private final TourListViewModel viewModel;
    public ListView<Tour> tourList;
    public Button editButton;
    public Button deleteButton;

    public TourListController(TourListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editButton.disableProperty().bind(viewModel.getEditIsDisabledProperty());
        deleteButton.disableProperty().bind(viewModel.getEditIsDisabledProperty());
        viewModel.addChangeListener(tourList);
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
    }
    public void openBlankFormButtonAction(){
        viewModel.openBlankFormButtonAction();
    }
    public void openFilledFormButtonAction() {viewModel.openFilledFormButtonAction();}
    public void deleteButtonAction() {viewModel.deleteButtonAction();}


}
