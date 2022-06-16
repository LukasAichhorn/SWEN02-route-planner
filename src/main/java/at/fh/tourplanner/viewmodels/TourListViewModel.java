package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.DataAccessLayer.TourRepository;
import at.fh.tourplanner.businessLayer.TourService;
import at.fh.tourplanner.controller.Tour.TourFormController;
import at.fh.tourplanner.listenerInterfaces.ListItemSelectiontListener;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.DataAccessLayer.DAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TourListViewModel {

    private List<ListItemSelectiontListener> eventListeners = new ArrayList<>();
    ObservableList<Tour> tours = FXCollections.observableArrayList();
    public ObservableList<Tour> getTours() {
        return tours;
    }

    public TourListViewModel() {
        // TODO route request over Tour service interface
        //create tour service interface
        //
        setTours(TourRepository.getInstance().getCachedToursList());
    }

    public void setTours(List<Tour> tourList) {
        tours.clear();
        tours.addAll(tourList);

    }
    public void addListener(ListItemSelectiontListener listItemSelectiontListener){
        this.eventListeners.add(listItemSelectiontListener);
    }
    public void publishSelectionEvent(Tour tour) {
        for(var listener : eventListeners){
            listener.fillForm(tour);
        }
    }
    public void addChangeListener(ListView listView){
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tour>() {
            @Override
            public void changed(ObservableValue<? extends Tour> observableValue, Tour tour, Tour t1) {
                publishSelectionEvent(t1);
                System.out.println("changeListener triggered with");
                System.out.println(t1);
            }
        });
    }

    public void saveTourToList(Tour tour) {
        //TODO implement DAL backend calls
        //call Tour Service add Tour -> called backend -> usw.
        TourRepository.getInstance().addTour(tour);
        tours.clear();
        setTours(TourRepository.getInstance().getCachedToursList());
        }

    public void editTour(Tour tour) {
        List<Tour> tempState = tours.stream().toList();
        System.out.println("Editing tour with UUID: " + tour.getUUID() );
        List<Tour> tourToEdit = tempState.stream().filter(t->t.getUUID().equals(tour.getUUID())).toList();
        if(tourToEdit.isEmpty()){
            System.out.println("tour with UUID: " + tour.getUUID() + "was not found in Oberservable list");
            return;
        }
        if(tourToEdit.size() > 1){
            System.out.println("Multiple tours have been found, '\n -result: " + tourToEdit );
            return;
        }
        tourToEdit.get(0).setUUID(tour.getUUID());
        tourToEdit.get(0).setName(tour.getName());
        tourToEdit.get(0).setStart(tour.getStart());
        tourToEdit.get(0).setDestination(tour.getDestination());
        tourToEdit.get(0).setDescription(tour.getDescription());
        tours.clear();
        tours.addAll(tempState);
        System.out.println(tours);



    }

    public void searchTours(String searchString) {
        var tours = DAO.getInstance().findMatchingTours(searchString);
        setTours(tours);
    }
}
