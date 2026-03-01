package xaviercanadas.bicingproject.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import xaviercanadas.bicingproject.dto.GenericResponse;
import xaviercanadas.bicingproject.service.NotifierService;

@Path("/notify")
public class NotifierResource {

    @GET
    @Path("/slots")
    @Produces(MediaType.APPLICATION_JSON)
    public Response notifyAvailableSlots(@QueryParam("phone") String phoneNumber) {

        if (phoneNumber == null || phoneNumber.isEmpty()) {
            GenericResponse response = new GenericResponse("The phone number is null or empty");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(response)
                    .build();
        }

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
