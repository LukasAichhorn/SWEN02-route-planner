package at.fh.tourplanner.controller.Log;

import at.fh.tourplanner.viewmodels.Logs.LogListViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class LogListController implements Initializable {
    private final LogListViewModel logListViewModel;
    public TableView logList;
    public Button editButton;
    public Button deleteButton;
    @FXML private TableColumn timeStamp;
    @FXML private TableColumn rating;
    @FXML private TableColumn difficulty;
    @FXML private TableColumn duration;
    @FXML private TableColumn comment;

    public LogListController(LogListViewModel logListViewModel) {
        this.logListViewModel = logListViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logListViewModel.addChangeListener(logList);
        System.out.println("setting items to table view");
        System.out.println(logListViewModel.getLogs());
        logList.setItems(logListViewModel.getLogs());

        timeStamp.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
       rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        difficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        comment.setCellValueFactory(new PropertyValueFactory<>("comment"));

    }
    public void openBlankLogFormButtonAction(){
        logListViewModel.openBlankLogFormButtonAction();
    }
    public void openFilledFormButtonAction(){}
    public void deleteButtonAction(){}
}
