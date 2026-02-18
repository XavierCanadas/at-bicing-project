package xaviercanadas.bicingproject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import xaviercanadas.bicingproject.model.bicing.Data;
import xaviercanadas.bicingproject.model.bicing.Station;
import org.apache.log4j.Logger;

import java.util.List;

@Path("/stations")
public class StationsResource {

    // Log4j Instance
    final static Logger logger = Logger.getLogger(StationsResource.class);

    // URL and Tokens
    private static final String BICING_URL = "https://opendata-ajuntament.barcelona.cat/";
    private static final String BICING_PATH = "data/dataset/6aa3416d-cela-494d-861b-7bd07f069600/resource/1b215493-9e63-4a12-8980-2d7e0fa19f85/download";
    private static final String BICING_TOKEN = System.getenv("TOKEN_BCN");

    // Cache variables
    private static List<Station> cachedStations = null;
    private static long lastCacheUpdate = 0;
    private static final long CACHE_DURATION = 120000; // 120 seconds

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStations() {
        long currentTime = System.currentTimeMillis();
        
        // Log4j: info
        logger.info("Request received: GET /stations");
        
        // 1. Check Cache
        if (cachedStations != null && (currentTime - lastCacheUpdate) < CACHE_DURATION) {
            long remaining = (CACHE_DURATION - (currentTime - lastCacheUpdate)) / 1000;
            logger.info("Returning data from cache. Time remaining: " + remaining + "s");
            return Response.ok(cachedStations).build();
        }

        // 2. Fetch from API
        logger.info("Cache empty or expired. Connecting to Bicing API...");
        
        try {
            if (BICING_TOKEN == null || BICING_TOKEN.isEmpty()) {
                // Log4j: error
                logger.error("Configuration Error: TOKEN_BCN environment variable not set.");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Configuration Error: Missing Token").build();
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
                return Response.ok(cachedStations).build();
            } else {
                logger.warn("Empty response from Bicing API.");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: Empty response from Bicing").build();
            }

        } catch (Exception e) {
            logger.error("Error connecting to Bicing: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error connecting to Bicing: " + e.getMessage()).build();
        }
    }
}