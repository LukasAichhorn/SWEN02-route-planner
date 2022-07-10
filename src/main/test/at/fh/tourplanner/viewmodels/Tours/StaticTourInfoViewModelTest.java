package at.fh.tourplanner.viewmodels.Tours;

import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StaticTourInfoViewModelTest {

    private StaticTourInfoViewModel staticTourInfoViewModelSpy;

    @BeforeEach
    void setUp() {
        this.staticTourInfoViewModelSpy = spy(StaticTourInfoViewModel.class);
        doNothing().when(staticTourInfoViewModelSpy).setImage("");

    }

    @Test
    void fillTourInfoWithValidTour() {
        //Arrange
        Tour testTour = new Tour(UUID.randomUUID(), "Testtour", "Wien", "Graz", "Just a test", TransportType.bicycle, "120", "12",null , "");
        //Act
        this.staticTourInfoViewModelSpy.fillTourInfo(testTour);
        //Assert
        assertEquals(testTour.getName(), this.staticTourInfoViewModelSpy.getTourName().getValue());
        assertEquals(testTour.getStart(), this.staticTourInfoViewModelSpy.getStart().getValue());
        assertEquals(testTour.getDestination(), this.staticTourInfoViewModelSpy.getDestination().getValue());
        assertEquals(testTour.getDescription(), this.staticTourInfoViewModelSpy.getDescription().getValue());
        assertEquals(testTour.getTransportType().toString(), this.staticTourInfoViewModelSpy.getSelectedTransportType().getValue());
        assertEquals(testTour.getDistance(), this.staticTourInfoViewModelSpy.getTourDistance().getValue());
        assertEquals(testTour.getEstimatedTime(), this.staticTourInfoViewModelSpy.getEstimatedTime().getValue());
    }

    @Test
    void tryFillingTourWithEmptyTourTest() {
        assertThrows(IllegalArgumentException.class, () -> {this.staticTourInfoViewModelSpy.fillTourInfo(null);});
    }

}