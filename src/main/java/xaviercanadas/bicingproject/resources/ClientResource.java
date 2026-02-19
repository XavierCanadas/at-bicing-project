package xaviercanadas.bicingproject.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.log4j.Logger;
import xaviercanadas.bicingproject.dto.GenericResponse;
import xaviercanadas.bicingproject.dto.SubscribeRequest;
import xaviercanadas.bicingproject.service.ClientService;

@Path("/clients")
public class ClientResource {

    private final static Logger logger = Logger.getLogger(ClientResource.class);


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClients() {
        logger.info("Received request to get all clients");
        return Response.ok(ClientService.getAllClients()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addClient(SubscribeRequest request) {
        logger.info("Received subscription request: " + request);

        if (request == null || request.phone() == null || request.telegramToken() == null || request.chatId() == 0) {
            logger.warn("Invalid subscription request: Missing required fields");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid request: Missing required fields").build();
        }

        try {
            ClientService.addClient(request);
            logger.info("Client added successfully: " + request.phone());
            GenericResponse genericResponse = new GenericResponse("Client added successfully");
            return Response.ok(genericResponse).build();

        } catch (Exception e) {
            logger.error("Error adding client: " + e.getMessage(), e);
            GenericResponse genericResponse = new GenericResponse("Error adding client: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(genericResponse).build();
        }
    }
}
