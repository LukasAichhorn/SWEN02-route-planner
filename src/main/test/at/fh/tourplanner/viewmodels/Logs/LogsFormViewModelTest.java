package at.fh.tourplanner.viewmodels.Logs;

import at.fh.tourplanner.businessLayer.logService.LogService;
import at.fh.tourplanner.businessLayer.logService.LogService_Interface;
import at.fh.tourplanner.businessLayer.validationService.FormValidationService;
import at.fh.tourplanner.model.DifficultyTier;
import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.viewmodels.Tours.TourFormViewModel;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogsFormViewModelTest {

    LogsFormViewModel logsFormViewModel;

    @BeforeEach
    void setUp() {
        //Arrange
        FormValidationService formValidationServiceMock = mock(FormValidationService.class);
        LogService logServiceMock = mock(LogService.class);
        this.logsFormViewModel = new LogsFormViewModel(formValidationServiceMock, logServiceMock);

    }

    @Test
    void fillFormWithLogTest() {
        //Arrange
        Log testLog = new Log(LocalDate.now(), 3, DifficultyTier.Anfaenger, 4, "Ok", 1);
        //Act
        logsFormViewModel.fillFormWithSelection(testLog, 1);
        //Assert
        assertEquals(testLog.getTourID(), logsFormViewModel.getTourID());
        assertEquals(testLog.getTimeStamp(), logsFormViewModel.getDate().getValue());
        assertEquals(testLog.getDuration(), Integer.parseInt(logsFormViewModel.getDuration().getValue()));
        assertEquals(testLog.getRating(), logsFormViewModel.getSelectedRating().getValue());
        assertEquals(testLog.getDifficulty(), logsFormViewModel.getSelectedDifficulty().getValue());
        assertEquals(testLog.getComment(), logsFormViewModel.getComment().getValue());
    }

    @Test
    void fillFormWithEmptyLogTest() {
        //Arrange
        LogsFormViewModel logsFormViewModelMock = mock(LogsFormViewModel.class);
        doCallRealMethod().when(logsFormViewModelMock).fillFormWithSelection(null, 0);
        //Act
        logsFormViewModelMock.fillFormWithSelection(null, 0);
        //Verify that clearForm is called
        verify(logsFormViewModelMock).clearForm();

    }

    @Test
    void clearFormTest() {
        //Act
        logsFormViewModel.clearForm();
        //Assert
        assertEquals(0, logsFormViewModel.getTourID());
        assertEquals(LocalDate.now(), logsFormViewModel.getDate().getValue());
        assertEquals("", logsFormViewModel.getComment().getValue());
        assertNull(logsFormViewModel.getDuration().getValue());
        assertNull(logsFormViewModel.getSelectedDifficulty().getValue());
        assertNull(logsFormViewModel.getSelectedRating().getValue());

    }


}