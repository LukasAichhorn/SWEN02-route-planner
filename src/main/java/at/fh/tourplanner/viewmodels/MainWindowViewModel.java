package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.ControllerFactory;
import at.fh.tourplanner.Main;
import at.fh.tourplanner.listenerInterfaces.*;
import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.viewmodels.Logs.LogListViewModel;
import at.fh.tourplanner.viewmodels.Logs.LogsFormViewModel;
import at.fh.tourplanner.viewmodels.Tours.StaticTourInfoViewModel;
import at.fh.tourplanner.viewmodels.Tours.TourFormViewModel;
import at.fh.tourplanner.viewmodels.Tours.TourListViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindowViewModel {

    private final TourFormViewModel tourFormViewModel;

    private final TourListViewModel tourListViewModel;

    private final SearchBarViewModel searchBarViewModel;
    private final LogsFormViewModel logsFormViewModel;
    private  final LogListViewModel logListViewModel;
    private final StaticTourInfoViewModel staticTourInfoViewModel;

    private final List<NewTourModeActionListener> newTourModeActionListeners = new ArrayList<>();

    public MainWindowViewModel(StaticTourInfoViewModel staticTourInfoViewModel, TourFormViewModel tourFormViewModel, TourListViewModel tourListViewModel, SearchBarViewModel searchBarViewModel, LogsFormViewModel logsFormViewModel,LogListViewModel logListViewModel) {
        this.tourFormViewModel = tourFormViewModel;
        this.tourListViewModel = tourListViewModel;
        this.searchBarViewModel = searchBarViewModel;
        this.logsFormViewModel = logsFormViewModel;
        this.logListViewModel = logListViewModel;
        this.staticTourInfoViewModel = staticTourInfoViewModel;


        //Section - listener
        this.tourFormViewModel.addCreateActionListener(new FormActionCreateListener() {
            @Override
            public void handleCreateAction(Tour formData) {
                tourListViewModel.saveTourToList(formData);
            }
        });
        this.tourFormViewModel.addEditActionListener(new FormActionEditListener() {
            @Override
            public void handleEditAction(Tour formData) {
                tourListViewModel.editTour(formData);
            }
        });
        this.tourListViewModel.addListener(new ListItemSelectiontListener<Tour>() {
            @Override
            public void fillForm(Tour tour) {
                tourListViewModel.setEditIsDisabled(false);
                tourFormViewModel.fillFormWithSelection(tour);
            }
        });
        this.tourListViewModel.addOpenBlankFormListener(new OpenBlankTourFormListener() {
            @Override
            public void handleEvent() {
                tourFormViewModel.clearForm();
                tourFormViewModel.openFormInWindow();
            }
        });
        this.tourListViewModel.addOpenFilledFormListener(new OpenFilledTourFormListener() {
            @Override
            public void handleEvent() {
                tourFormViewModel.openFormInWindow();
            }
        });
        this.tourListViewModel.addListener(new ListItemSelectiontListener<Tour>() {
            @Override
            public void fillForm(Tour tour) {
                staticTourInfoViewModel.fillTourInfo(tour);
            }
        });
        this.tourListViewModel.addListener(new ListItemSelectiontListener<Tour>() {
            @Override
            public void fillForm(Tour tour) {
                System.out.println("published Tour:");
                System.out.println(tour);
                logListViewModel.setLogs(tour.getLogs());
            }
        });
        this.tourListViewModel.addListener(new ListItemSelectiontListener<Tour>() {
            @Override
            public void fillForm(Tour tour) {
                System.out.println("Clearing Form:");
                logsFormViewModel.clearForm();
            }
        });
        this.logListViewModel.addListener(new ListItemSelectiontListener<Log>() {
            @Override
            public void fillForm(Log log) {
                logsFormViewModel.fillFormWithSelection(log);
            }
        });

        this.searchBarViewModel.addSearchListener(new SearchListener() {
            @Override
            public void handleSearch(String searchString) {
                tourListViewModel.searchTours(searchString);
            }
        });

        newTourModeActionListeners.add(new NewTourModeActionListener() {
            @Override
            public void handleNewTourModeAction() {
                tourFormViewModel.handleNewTourMode();
            }
        });

    }


    public void publishNewTourModeEvent() {
        for(var listener : newTourModeActionListeners) {
            listener.handleNewTourModeAction();
        }
    }
    public void openFormWindow(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/at/fh/tourplanner/tourForm.fxml"));
            fxmlLoader.setControllerFactory(controller -> ControllerFactory.getInstance().create(controller));
            GridPane gridPane = fxmlLoader.load();
            Stage modal_stage = new Stage();
            modal_stage.setScene(new Scene(gridPane, 500, 575));
            modal_stage.setTitle("modal");
            modal_stage.initModality(Modality.APPLICATION_MODAL);
            modal_stage.initOwner(modal_stage.getOwner());
            modal_stage.setResizable(false);
            modal_stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
