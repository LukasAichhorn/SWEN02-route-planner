package at.fh.tourplanner.businessLayer.searchService;

import at.fh.tourplanner.DataAccessLayer.DAO;
import at.fh.tourplanner.model.Tour;
import at.fh.tourplanner.model.TransportType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SearchServiceTest {
    //create data
    private static final String NORESULT = "noResult";
    private static final String RESULT = "testTour";
    private static final Tour TOUR = new Tour(
            UUID.fromString("081d523b-1ca1-414e-a225-70f8f2b03f78"),
            "testTour", "Wien","Graz","description_text", TransportType.bicycle,
            "120","HH23",new ArrayList<>(),"tourImagePathString");
    private static final List<Tour> TOURLIST = new ArrayList<>();
    //used Fields
    private static DAO database;
    private static SearchService searchService;
    //Mocking
    private static DAO createDatabase (){
        DAO mock = mock(DAO.class);
        when(mock.getAllTours()).thenReturn(TOURLIST);
        return mock;
    }
    @BeforeAll
    public static void init() {
        TOURLIST.add(TOUR);
        database = createDatabase();
        searchService = new SearchService(database);
    }

    @Test
    void FindMatchingToursWithNoResult() {
        List<Tour>searchResult = searchService.findMatchingTours(NORESULT);
        assertEquals(0,searchResult.size());

    }
    @Test
    void FindMatchingToursWithResult() {
        List<Tour>searchResult = searchService.findMatchingTours(RESULT);
        assertEquals(1,searchResult.size());
    }
    @Test
    void verfiyFunctionCall(){
        verify(database,times(2)).getAllTours();
    }
}