package at.fh.tourplanner.businessLayer.validationService;

import at.fh.tourplanner.model.DifficultyTier;
import at.fh.tourplanner.model.FormDataNewTour;
import at.fh.tourplanner.model.LogFormData;
import at.fh.tourplanner.model.TransportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FormValidationServiceImpTest {

    FormValidationService formValidationService;

    @BeforeEach
    void setUp() {
        formValidationService = new FormValidationServiceImp();
    }

    @Test
    void testValidTourForm() {
        //Arrange
        FormDataNewTour testTour = new FormDataNewTour("Test", TransportType.bicycle, "Wien", "Graz", "Nice");
        boolean tourIsValid;
        //Act
        tourIsValid = formValidationService.noEmptyValues(testTour);
        //Assert
        assertTrue(tourIsValid);
    }

    @Test
    void testWhitespaceAsParameterValidation() {
        //Arrange
        FormDataNewTour testTour = new FormDataNewTour("          ", TransportType.bicycle, "Wien", "Graz", "Nice");
        boolean tourIsValid;
        //Act
        tourIsValid = formValidationService.noEmptyValues(testTour);
        //Assert
        assertFalse(tourIsValid);
    }

    @Test
    void testEmptyStringAsParameterValidation() {
        //Arrange
        FormDataNewTour testTour = new FormDataNewTour("TestTour", TransportType.bicycle, "", "Graz", "Nice");
        boolean tourIsValid;
        //Act
        tourIsValid = formValidationService.noEmptyValues(testTour);
        //Assert
        assertFalse(tourIsValid);
    }

    @Test
    void testNoTransportTypeValidation() {
        //Arrange
        FormDataNewTour testTour = new FormDataNewTour("TestTour", null, "Wien", "Graz", "Nice");
        boolean tourIsValid;
        //Act
        tourIsValid = formValidationService.noEmptyValues(testTour);
        //Assert
        assertFalse(tourIsValid);
    }

    @Test
    void testWhitespaceAsParameterValidationForLogs() {
        //Arrange
        LogFormData testLog = new LogFormData(LocalDate.now(), 1, DifficultyTier.Anfaenger, "              ", "Comment");
        boolean logIsValid;
        //Act
        logIsValid = formValidationService.noEmptyValues(testLog);
        //Assert
        assertFalse(logIsValid);
    }

    @Test
    void testEmptyStringAsParameterValidationForLogs() {
        //Arrange
        LogFormData testLog = new LogFormData(LocalDate.now(), 1, DifficultyTier.Anfaenger, "", "Comment");
        boolean logIsValid;
        //Act
        logIsValid = formValidationService.noEmptyValues(testLog);
        //Assert
        assertFalse(logIsValid);
    }

    @Test
    void testNoDifficultyTierValidation() {
        //Arrange
        LogFormData testLog = new LogFormData(LocalDate.now(), 1, null, "12h", "Comment");
        boolean logIsValid;
        //Act
        logIsValid = formValidationService.noEmptyValues(testLog);
        //Assert
        assertFalse(logIsValid);
    }

    @Test
    void testInvalidRatingValidation() {
        //Arrange
        LogFormData testLog = new LogFormData(LocalDate.now(), 0, DifficultyTier.Anfaenger, "12h", "Comment");
        boolean logIsValid;
        //Act
        logIsValid = formValidationService.noEmptyValues(testLog);
        //Assert
        assertFalse(logIsValid);
    }

    @Test
    void testValidLogForm() {
        //Arrange
        LogFormData testLog = new LogFormData(LocalDate.now(), 1, DifficultyTier.Anfaenger, "12h", "Comment");
        boolean logIsValid;
        //Act
        logIsValid = formValidationService.noEmptyValues(testLog);
        //Assert
        assertTrue(logIsValid);
    }







}