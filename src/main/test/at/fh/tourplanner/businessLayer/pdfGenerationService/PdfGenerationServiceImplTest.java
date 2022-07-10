package at.fh.tourplanner.businessLayer.pdfGenerationService;

import at.fh.tourplanner.DataAccessLayer.DAO;
import at.fh.tourplanner.model.DifficultyTier;
import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PdfGenerationServiceImplTest {

    PdfGenerationServiceImpl pdfGenerationService;

    @BeforeEach
    public void setup() {
        DAO mockRepo = mock(DAO.class);
        this.pdfGenerationService = new PdfGenerationServiceImpl(mockRepo);
    }

    @Test
    void logTableGenerationWithLogListTest() {
        //Arrange
        List<Log> testlogs = new ArrayList<>();
        testlogs.add(new Log(LocalDate.now(), 3, DifficultyTier.Anfaenger, 2, "test1", 1));
        testlogs.add(new Log(LocalDate.now(), 3, DifficultyTier.Anfaenger, 2, "test2", 1));
        testlogs.add(new Log(LocalDate.now(), 3, DifficultyTier.Anfaenger, 2, "test3", 1));
        Tour testTour = new Tour(UUID.randomUUID(), "Testtour", "Wien", "Graz", "Just a test", TransportType.bicycle, "120", "12",testlogs , "C:\\Users\\goell\\OneDrive\\Dokumente\\FH\\BIFSEM4\\TestMap");
        Table testTable;
        //act
        testTable = pdfGenerationService.generateTableForTourLogs(testTour);
        //assert
        assertEquals(testlogs.size(), testTable.getNumberOfRows());
    }

    @Test
    void statisticsTableGenerationTest() {
        //Arrange
        List<Tour> testDB = new ArrayList<>();
        testDB.add(new Tour(UUID.randomUUID(), "Testtour1", "Wien", "Graz", "Just a test", TransportType.bicycle, "120", "12",new ArrayList<>() , "C:\\Users\\goell\\OneDrive\\Dokumente\\FH\\BIFSEM4\\TestMap"));
        testDB.add(new Tour(UUID.randomUUID(), "Testtour2", "Wien", "Graz", "Just a test", TransportType.bicycle, "120", "12",new ArrayList<>() , "C:\\Users\\goell\\OneDrive\\Dokumente\\FH\\BIFSEM4\\TestMap"));
        testDB.add(new Tour(UUID.randomUUID(), "Testtour3", "Wien", "Graz", "Just a test", TransportType.bicycle, "120", "12",new ArrayList<>() , "C:\\Users\\goell\\OneDrive\\Dokumente\\FH\\BIFSEM4\\TestMap"));
        Table testTable;
        //Act
        testTable = pdfGenerationService.generateStatisticsTable(testDB);
        //Assert
        assertEquals(testDB.size(), testTable.getNumberOfRows());
    }


}