package xaviercanadas.bicingproject.service;

import org.apache.log4j.Logger;
import xaviercanadas.bicingproject.model.bicing.Station;

import java.util.List;

public class StationService {

    private static Logger logger = Logger.getLogger(StationService.class);

    public static List<Station> getAllStations() {
        return List.of();
    }

    public static List<Station> getStationsByIds(List<Integer> station_ids) {
        return List.of();
    }
}
