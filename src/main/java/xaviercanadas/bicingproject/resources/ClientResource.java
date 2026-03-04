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
import xaviercanadas.bicingproject.service.OpenGatewayService;

@Path("/clients")
public class ClientResource {

    private final static Logger logger = Logger.getLogger(ClientResource.class);


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClients() {
        logger.info("Received request to get all clients");
        return Response.ok(ClientService.getAllClients()).build();
    }

    private boolean isValidRequest(SubscribeRequest request) {
        if (request == null) return false;
        if (request.phone() == null || request.phone().isBlank()) return false;
        if (!request.phone().matches("\\+?[0-9]{7,15}")) return false;
        if (request.telegramToken() == null || request.telegramToken().isBlank()) return false;
        if (request.chatId() == 0) return false;
        if (request.stationsIds() == null || request.stationsIds().isEmpty()) return false;
        return true;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addClient(SubscribeRequest request) {
        logger.info("Received subscription request: " + request);

        if (!isValidRequest(request)) {
            logger.warn("Invalid subscription request: Missing or invalid required fields");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new GenericResponse("Invalid request: Missing or invalid required fields")).build();
        }

        logger.info("Verifying user's age via Open Gateway...");
        boolean isAdult = OpenGatewayService.isAdult(request.phone());
        
        if (!isAdult) {
            logger.warn("The client with phone number " + request.phone() + " did not pass the age verification.");
            GenericResponse errorResponse = new GenericResponse("You must be an adult to subscribe (over 25).");
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(errorResponse).build();
        }
        String internationalPhoneNumber = request.phone().startsWith("+") ? request.phone() : "+34" + request.phone();
        boolean isCorrectName = OpenGatewayService.isCorrectName(internationalPhoneNumber,  request.name());
        if (!isCorrectName) {
            logger.warn("The client with phone number " + request.phone() + " does not belong to ." + request.name());
            GenericResponse errorResponse = new GenericResponse("You phone and name do not match.");
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(errorResponse).build();
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
