package at.fh.tourplanner.viewmodels.Tours;

import at.fh.tourplanner.businessLayer.mapApiService.DirectionService;
import at.fh.tourplanner.businessLayer.mapApiService.TourImageService;
import at.fh.tourplanner.businessLayer.tourService.TourService;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TourFormViewModelTest {

    private TourFormViewModel tourFormViewModel;


    void setUp() {
        DirectionService dsMock = mock(DirectionService.class);
        TourService tsMock = mock(TourService.class);
        TourImageService tiMock = mock(TourImageService.class);
        this.tourFormViewModel = new TourFormViewModel(dsMock, tsMock, tiMock);
    }

    @Test
    void fillFormWithSelectionTest() {
        //Arrange
        setUp();
        Tour testTour = new Tour(UUID.randomUUID(), "Testtour", "Wien", "Graz", "Just a test", TransportType.bicycle, "120", "12",null , "C:\\Users\\goell\\OneDrive\\Dokumente\\FH\\BIFSEM4\\TestMap");
        //Act
        tourFormViewModel.fillFormWithSelection(testTour);
        //Assert
        assertEquals(testTour.getName(), tourFormViewModel.getTourName().getValue());
        assertEquals(testTour.getUUID().toString(), tourFormViewModel.getTourUUID());
        assertEquals(testTour.getStart(), tourFormViewModel.getStart().getValue());
        assertEquals(testTour.getDestination(), tourFormViewModel.getDestination().getValue());
        assertEquals(testTour.getDescription(), tourFormViewModel.getDescription().getValue());
        assertEquals(testTour.getTransportType(), tourFormViewModel.getSelectedTransportType().getValue());
    }

    @Test
    void fillFormWithEmptyTourTest() {
        //Arrange
        TourFormViewModel tourFormViewModelMock = mock(TourFormViewModel.class);
        doCallRealMethod().when(tourFormViewModelMock).fillFormWithSelection(null);
        //Act
        tourFormViewModelMock.fillFormWithSelection(null);
        //Verify that clearForm is called
        verify(tourFormViewModelMock).clearForm();

    }

    @Test
    void clearFormTest() {
        //Arrange
        setUp();
        //Act
        tourFormViewModel.clearForm();
        //Assert
        assertEquals("", tourFormViewModel.getTourUUID());
        assertEquals("", tourFormViewModel.getTourName().getValue());
        assertEquals("", tourFormViewModel.getStart().getValue());
        assertEquals("", tourFormViewModel.getDestination().getValue());
        assertEquals("", tourFormViewModel.getDescription().getValue());
        assertNull(tourFormViewModel.getSelectedTransportType().getValue());
    }





}