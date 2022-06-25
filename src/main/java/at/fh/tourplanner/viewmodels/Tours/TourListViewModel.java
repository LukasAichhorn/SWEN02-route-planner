package at.fh.tourplanner.viewmodels.Tours;

import at.fh.tourplanner.DataAccessLayer.listener.DbCreateEvent;
import at.fh.tourplanner.businessLayer.TourService;
import at.fh.tourplanner.listenerInterfaces.ListItemSelectionListener;
import at.fh.tourplanner.listenerInterfaces.OpenBlankTourFormListener;
import at.fh.tourplanner.listenerInterfaces.OpenFilledTourFormListener;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.DataAccessLayer.InMemoryDAO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ListView;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TourListViewModel {
    private Tour currentSelection = null;

    private final List<ListItemSelectionListener> eventListeners = new ArrayList<>();
    private final List<OpenBlankTourFormListener> openBlankTourFormListeners =
            new ArrayList<>();
    private final List<OpenFilledTourFormListener> openFilledTourFormListeners =
            new ArrayList<>();
    ObservableList<Tour> tours = FXCollections.observableArrayList();
    private final BooleanProperty editIsDisabled = new SimpleBooleanProperty(true);
    private final TourService tourService;
    private final UiDeleteRouteService uiDeleteRouteService;

    public TourListViewModel(TourService tourService) {
        this.tourService = tourService;
        this.uiDeleteRouteService = new UiDeleteRouteService();
        refreshListView();
        uiDeleteRouteService.valueProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal != null) {
                refreshListView();
            }
        });
    }

    public ObservableList<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tourList) {
        tours.clear();
        tours.addAll(tourList);

    }

    public Tour getCurrentSelection() {
        return currentSelection;
    }

    public void setCurrentSelection(Tour currentSelection) {
        this.currentSelection = currentSelection;
    }

    public void refreshListView() {
        tours.clear();
        tours.addAll(tourService.getToursFromDatabase());
    }

    public void addListener(ListItemSelectionListener listItemSelectionListener) {
        this.eventListeners.add(listItemSelectionListener);
    }

    public void addOpenBlankFormListener(OpenBlankTourFormListener openBlankTourFormListener) {
        this.openBlankTourFormListeners.add(openBlankTourFormListener);
    }

    public void addOpenFilledFormListener(OpenFilledTourFormListener openFilledTourFormListener) {
        this.openFilledTourFormListeners.add(openFilledTourFormListener);
    }

    public void publishSelectionEvent(Tour tour) {
        for (var listener : eventListeners) {
            listener.fillForm(tour);
        }
    }

    public void publishOpenBlankTourFormEvent() {
        for (var listener : openBlankTourFormListeners) {
            listener.handleEvent();
        }
    }

    public void publishOpenFilledTourFormEvent() {
        for (var listener : openFilledTourFormListeners) {
            listener.handleEvent();
        }
    }

    public void addChangeListener(ListView<Tour> listView) {
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tour>() {
            @Override
            public void changed(ObservableValue<? extends Tour> observableValue, Tour tour, Tour t1) {
                currentSelection = t1;
                System.out.println("current selection : ");
                System.out.println(t1);
                publishSelectionEvent(t1);
                System.out.println("changeListener triggered with");
                System.out.println(t1);
            }
        });
    }

    public void searchTours(String searchString) {
        var tours = InMemoryDAO.getInstance().findMatchingTours(searchString);
        setTours(tours);
    }

    public void openBlankFormButtonAction() {
        publishOpenBlankTourFormEvent();
    }

    public BooleanProperty getEditIsDisabledProperty() {
        return editIsDisabled;
    }

    public void setEditIsDisabled(boolean canEdit) {
        this.editIsDisabled.set(canEdit);
    }

    public void openFilledFormButtonAction() {
        publishOpenFilledTourFormEvent();
    }

    public void deleteButtonAction() {
        uiDeleteRouteService.restart();
    }

    @AllArgsConstructor
    @Setter
    public class UiDeleteRouteService extends Service<String> {


        @Override
        protected Task<String> createTask() {
            return new Task<>() {
                protected String call() {
                    System.out.println("starting task deleting id: " + getCurrentSelection().getUUID());
                    tourService.deleteTourInDatabase(getCurrentSelection().getUUID());
                    return "completed delete Task";
                }
            };
        }
    }
}
