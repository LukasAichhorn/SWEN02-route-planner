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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class TourListViewModel {

    private final List<ListItemSelectionListener> eventListeners = new ArrayList<>();
    private final List<OpenBlankTourFormListener> openBlankTourFormListeners =
            new ArrayList<>();
    private final List<OpenFilledTourFormListener> openFilledTourFormListeners =
            new ArrayList<>();
    ObservableList<Tour> tours = FXCollections.observableArrayList();
    private final BooleanProperty editIsDisabled = new SimpleBooleanProperty(true);
    private final TourService tourService;

    public TourListViewModel(TourService tourService) {
        this.tourService = tourService;
        refreshListView();
        tourService.addCreateListener(new DbCreateEvent() {
            @Override
            public void onCreate() {
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
    private void refreshListView(){
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

    public void addChangeListener(ListView listView) {
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tour>() {
            @Override
            public void changed(ObservableValue<? extends Tour> observableValue, Tour tour, Tour t1) {
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

}
