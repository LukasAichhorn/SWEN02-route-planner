package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.businessLayer.searchService.SearchService;
import at.fh.tourplanner.listenerInterfaces.SearchListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;

public class SearchBarViewModel {

    private final StringProperty searchString = new SimpleStringProperty("");
    private final BooleanProperty isButtonDisabled = new SimpleBooleanProperty(true);
    private final List<SearchListener> searchListenerList = new ArrayList<>();

    public StringProperty getSearchString() { return searchString; }

    public void addSearchListener(SearchListener searchListener){
        searchListenerList.add(searchListener);
    }
    public SearchBarViewModel(){
        searchString.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                publishSearchEvent();
            }
        });
    }

    public void publishSearchEvent() {
        for(var listener : searchListenerList){
            listener.handleSearch(this.searchString.get());
        }
        checkButtonStatus();
    }

    public void clearSearchBar() {
        searchString.set("");
        publishSearchEvent();
    }

    public Property<Boolean> getIsButtonDisabledBoolean() {
        return this.isButtonDisabled;
    }

    public void checkButtonStatus() {
        if(!this.searchString.get().isEmpty() && this.searchString.get() != null) {
            this.isButtonDisabled.set(false);
        }
        else{
            this.isButtonDisabled.set(true);
        }
    }
}
