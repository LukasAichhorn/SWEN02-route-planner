package at.fh.tourplanner.controller;

import at.fh.tourplanner.viewmodels.SearchBarViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchBarController implements Initializable {

    private final SearchBarViewModel searchBarViewModel;

    public TextField searchBarTextField;

    public Button clearSearchBarButton;

    public SearchBarController(SearchBarViewModel searchBarViewModel){
        this.searchBarViewModel = searchBarViewModel;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchBarTextField.textProperty().bindBidirectional(searchBarViewModel.getSearchString());
        clearSearchBarButton.disableProperty().bindBidirectional(searchBarViewModel.getIsButtonDisabledBoolean());
    }

    public void searchAction() {
        searchBarViewModel.publishSearchEvent();
    }

    public void clearSearch(ActionEvent actionEvent) {
        searchBarViewModel.clearSearchBar();
    }
}
