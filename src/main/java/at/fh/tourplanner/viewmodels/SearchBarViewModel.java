package at.fh.tourplanner.viewmodels;

import at.fh.tourplanner.listenerInterfaces.SearchListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class SearchBarViewModel {

    private final StringProperty searchString = new SimpleStringProperty("");

    private final List<SearchListener> searchListenerList = new ArrayList<>();

    public StringProperty getSearchString() { return searchString; }

    public void addSearchListener(SearchListener searchListener){
        searchListenerList.add(searchListener);
    }

    public void publishSearchEvent() {
        for(var listener : searchListenerList){
            listener.handleSearch(this.searchString.get());
        }
    }


}
