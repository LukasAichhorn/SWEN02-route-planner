package at.fh.tourplanner.DataAccessLayer.mapAPI.Retrofit;

import javafx.scene.image.Image;
public class MapAPIDataWrapper {
    public MapAPIDataWrapper(Route route, Image routeMap) {
        this.route = route;
        this.routeMap = routeMap;
    }

    private Route route;
    private Image routeMap;

    public Route getRoute() {
        return route;
    }

    public Image getRouteMap() {
        return routeMap;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setRouteMap(Image routeMap) {
        this.routeMap = routeMap;
    }
}

