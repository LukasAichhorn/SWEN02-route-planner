package at.fh.tourplanner.controller;

import at.fh.tourplanner.viewmodels.SearchBarViewModel;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchBarController implements Initializable {

    private final SearchBarViewModel searchBarViewModel;

    public TextField searchBarTextField;

    public SearchBarController(SearchBarViewModel searchBarViewModel){
        this.searchBarViewModel = searchBarViewModel;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchBarTextField.textProperty().bindBidirectional(searchBarViewModel.getSearchString());
    }

    public void searchAction() {
        searchBarViewModel.publishSearchEvent();
    }
}
