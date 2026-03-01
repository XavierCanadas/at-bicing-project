package xaviercanadas.bicingproject.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import xaviercanadas.bicingproject.dto.GenericResponse;
import xaviercanadas.bicingproject.model.bicing.Data;
import xaviercanadas.bicingproject.model.bicing.Station;
import org.apache.log4j.Logger;
import xaviercanadas.bicingproject.service.StationService;

import java.util.List;

@Path("/stations")
public class StationsResource {

    // Log4j Instance
    final static Logger logger = Logger.getLogger(StationsResource.class);


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStations() {

        logger.info("Request received: GET /stations");
        
        try {
            List<Station> stations = StationService.getAllStations();
            return Response.status(Response.Status.OK)
                    .entity(stations)
                    .build();

        } catch (InternalServerErrorException e) {
            GenericResponse message = new GenericResponse(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(message)
                    .build();

        } catch (Exception e) {
            logger.error("Error connecting to Bicing: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error connecting to Bicing: " + e.getMessage()).build();
        }
    }
}