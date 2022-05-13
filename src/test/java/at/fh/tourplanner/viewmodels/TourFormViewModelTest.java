package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.model.Tour;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TourFormViewModelTest {

    private final TourFormViewModel instance = new TourFormViewModel();

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTourName() {
    }

    @Test
    void getStart() {
    }

    @Test
    void getDestination() {
    }

    @Test
    void getDescription() {
    }

    @Test
    void getFormData() {
        //Arrange
        Tour tour;
        Tour expectedElement = new Tour("Ausflug", "Wien", "Graz", "Spass");
        instance.getTourName().set("Ausflug");
        instance.getStart().set("Wien");
        instance.getDestination().set("Graz");
        instance.getDescription().set("Spass");

        //Act

        //Assert



    }

    @Test
    void clearForm() {
    }
}