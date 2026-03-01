package xaviercanadas.bicingproject.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import xaviercanadas.bicingproject.dto.GenericResponse;
import xaviercanadas.bicingproject.service.NotifierService;

@Path("/notify")
public class NotifierResource {

    @GET
    @Path("/slots")
    @Produces(MediaType.APPLICATION_JSON)
    public Response notifyAvailableSlots(String phoneNumber) {

        try {
            NotifierService.notifySlots(phoneNumber);
            GenericResponse response = new GenericResponse("Update sent successfully to Telegram");
            return Response.status(Response.Status.NO_CONTENT)
                    .entity(response)
                    .build();

        } catch (NotFoundException e) {
            GenericResponse response = new GenericResponse(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(response)
                    .build();

        } catch (Exception e) {
            GenericResponse response = new GenericResponse(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(response)
                    .build();
        }
    }

}
