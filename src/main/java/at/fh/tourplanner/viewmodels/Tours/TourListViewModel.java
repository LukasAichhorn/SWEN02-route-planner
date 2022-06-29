package at.fh.tourplanner.viewmodels.Tours;

import at.fh.tourplanner.businessLayer.tourService.TourService;
import at.fh.tourplanner.listenerInterfaces.ListItemSelectionListener;
import at.fh.tourplanner.listenerInterfaces.ListUpdateListener;
import at.fh.tourplanner.listenerInterfaces.OpenBlankTourFormListener;
import at.fh.tourplanner.listenerInterfaces.OpenFilledTourFormListener;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.DataAccessLayer.InMemoryDAO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ListView;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class TourListViewModel {
    private Tour currentSelection = null;

    private final List<ListItemSelectionListener> selectionEventListeners = new ArrayList<>();
    private final List<ListUpdateListener>  updateListeners = new ArrayList<>();
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
        // TODO wrap refresh into a service
        tours.addAll(tourService.getToursFromDatabase());
    }

    public void addSelectionListener(ListItemSelectionListener listItemSelectionListener) {
        this.selectionEventListeners.add(listItemSelectionListener);
    }
    public void addUpdateListener(ListUpdateListener listUpdateListener) {
        this.updateListeners.add(listUpdateListener);
    }

    public void addOpenBlankFormListener(OpenBlankTourFormListener openBlankTourFormListener) {
        this.openBlankTourFormListeners.add(openBlankTourFormListener);
    }

    public void addOpenFilledFormListener(OpenFilledTourFormListener openFilledTourFormListener) {
        this.openFilledTourFormListeners.add(openFilledTourFormListener);
    }

    public void publishSelectionEvent(Tour tour) {
        for (var listener : selectionEventListeners) {
            listener.handleListItemSelection(tour);
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
                publishSelectionEvent(t1);
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
                    log.info("Deleting tour with UUID: {}", getCurrentSelection().getUUID());
                    tourService.deleteTourInDatabase(getCurrentSelection().getUUID());
                    return "completed delete Task";
                }
            };
        }
    }
}
