package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.businessLayer.searchService.SearchService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchBarViewModelTest {

    @Test
    void checkButtonStatusWithSearchString() {
        //arrange
        SearchBarViewModel searchBarViewModel = new SearchBarViewModel();
        searchBarViewModel.getSearchString().set("Test");
        //act
        searchBarViewModel.checkButtonStatus();
        //assert
        assertEquals(false, searchBarViewModel.getIsButtonDisabledBoolean().getValue());
    }

    @Test
    void checkButtonStatusWithEmptySearchString() {
        //arrange
        SearchBarViewModel searchBarViewModel = new SearchBarViewModel();
        searchBarViewModel.getSearchString().set("");
        //act
        searchBarViewModel.checkButtonStatus();
        //assert
        assertEquals(true, searchBarViewModel.getIsButtonDisabledBoolean().getValue());
    }

    @Test
    void clearSearchbarTest() {
        //arrange
        String testSearchString = "Test";
        SearchBarViewModel searchBarViewModelSpy = spy(SearchBarViewModel.class);
        doNothing().when(searchBarViewModelSpy).publishSearchEvent();
        searchBarViewModelSpy.getSearchString().set(testSearchString);
        //act
        searchBarViewModelSpy.clearSearchBar();
        //assert
        assertEquals("", searchBarViewModelSpy.getSearchString().getValue());
        assertNotEquals(testSearchString, searchBarViewModelSpy.getSearchString().getValue());
    }
}