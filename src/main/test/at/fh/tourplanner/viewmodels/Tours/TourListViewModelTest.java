package at.fh.tourplanner.viewmodels.Tours;

import at.fh.tourplanner.businessLayer.searchService.SearchService_Interface;
import at.fh.tourplanner.businessLayer.tourService.TourService_Interface;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TourListViewModelTest {

    TourListViewModel tourListViewModel;

    void setup() {
        List<Tour> testDB = new ArrayList<>();
        testDB.add(new Tour(UUID.randomUUID(), "Testtour", "Wien", "Graz", "Just a test", TransportType.bicycle, "120", "12",null , "C:\\Users\\goell\\OneDrive\\Dokumente\\FH\\BIFSEM4\\TestMap"));
        testDB.add(new Tour(UUID.randomUUID(), "Test", "Wien", "Graz", "Just a test", TransportType.bicycle, "120", "12",null , "C:\\Users\\goell\\OneDrive\\Dokumente\\FH\\BIFSEM4\\TestMap"));
        SearchService_Interface searchService_mock = mock(SearchService_Interface.class);
        TourService_Interface tourService_mock = mock(TourService_Interface.class);
        tourListViewModel = new TourListViewModel(tourService_mock, searchService_mock);
        when(tourService_mock.getToursFromDatabase()).thenReturn(testDB);
        when(searchService_mock.findMatchingTours("noResult")).thenReturn(Collections.emptyList());
    }

    @Test
    void refreshListViewTest() {
        //Arrange
        setup();
        int previousListSize = tourListViewModel.getTours().size();
        //Act
        tourListViewModel.refreshListView();
        //Assert
        assertEquals(2, tourListViewModel.getTours().size());
        assertNotEquals(previousListSize, tourListViewModel.getTours().size());
    }

    @Test
    void filterToursTest() {
        //Arrange
        setup();
        //Act
        tourListViewModel.filterTours("NoResult");
        //Assert
        assertEquals(0, tourListViewModel.getTours().size());
    }
}