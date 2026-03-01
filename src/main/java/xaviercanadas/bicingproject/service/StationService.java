package xaviercanadas.bicingproject.service;

import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import xaviercanadas.bicingproject.model.bicing.Data;
import xaviercanadas.bicingproject.model.bicing.Station;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StationService {

    private static Logger logger = Logger.getLogger(StationService.class);

    // URL and Tokens
    private static final String BICING_URL = "https://opendata-ajuntament.barcelona.cat/";
    private static final String BICING_PATH = "data/dataset/6aa3416d-cela-494d-861b-7bd07f069600/resource/1b215493-9e63-4a12-8980-2d7e0fa19f85/download";
    private static final String BICING_TOKEN = System.getenv("TOKEN_BCN");

    // Cache variables
    private static List<Station> cachedStations = null;
    private static long lastCacheUpdate = 0;
    private static final long CACHE_DURATION = 120000; // 120 seconds

    public static List<Station> getAllStations() {
        long currentTime = System.currentTimeMillis();

        // 1. Check Cache
        if (cachedStations != null && (currentTime - lastCacheUpdate) < CACHE_DURATION) {
            long remaining = (CACHE_DURATION - (currentTime - lastCacheUpdate)) / 1000;
            logger.info("Returning data from cache. Time remaining: " + remaining + "s");
            return cachedStations;
        }

        // 2. Fetch from API
        logger.info("Cache empty or expired. Connecting to Bicing API...");

        if (BICING_TOKEN == null || BICING_TOKEN.isEmpty()) {
            logger.error("Configuration Error: TOKEN_BCN environment variable not set.");
            throw new InternalServerErrorException("Configuration Error: TOKEN_BCN environment variable not set.");
        }

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(BICING_URL).path(BICING_PATH);

        Data responseData = target.request(MediaType.APPLICATION_JSON)
                .header("Authorization", BICING_TOKEN)
                .get(Data.class);

        if (responseData != null && responseData.data() != null) {
            // Update Cache
            cachedStations = responseData.data().stations();
            lastCacheUpdate = System.currentTimeMillis();

            logger.info("API response received. " + cachedStations.size() + " stations retrieved.");
            return cachedStations;
        } else {
            logger.warn("Empty response from Bicing API.");
            throw new InternalServerErrorException("Empty response from Bicing API.");
        }
    }


    /**
     * Retrieves the stations from the getAllStations list.
     * This approach is not optimal but for this lab is ok.
     * Maybe in the api there is a specific endpoint to retrieve only a set of stations by its id.
     */
    public static List<Station> getStationsByIds(List<Integer> station_ids) {
        if (station_ids == null || station_ids.isEmpty()) {
            return List.of();
        }

        List<Station> stations = getAllStations();

        Set<Integer> ids = new HashSet<>(station_ids);

        List<Station> filteredStations = new ArrayList<>();
        for (Station s : stations) {
            if (ids.contains(s.station_id())) {
                filteredStations.add(s);
            }
        }

        return filteredStations;
    }
}
