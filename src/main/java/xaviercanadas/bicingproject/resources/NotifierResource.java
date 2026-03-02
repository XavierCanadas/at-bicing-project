package xaviercanadas.bicingproject.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import xaviercanadas.bicingproject.dto.GenericResponse;
import xaviercanadas.bicingproject.service.NotifierService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;

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
            return Response.status(Response.Status.OK)
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

    @GET
    @Path("/air")
    @Produces(MediaType.APPLICATION_JSON)
    public Response notifyAirQuality(@QueryParam("phone") String phoneNumber, @Context HttpServletRequest request) {

        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new GenericResponse("The phone number is missing")).build();
        }

        // Obtain client's IP
        String ipAddress = request.getRemoteAddr();
        
        if (ipAddress.equals("0:0:0:0:0:0:0:1") || ipAddress.equals("127.0.0.1")) {
            ipAddress = "84.88.0.1"; // We use a real IP from Barcelona for testing purposes when running locally
        }

        try {
            NotifierService.notifyAirQuality(phoneNumber, ipAddress);
            return Response.status(Response.Status.OK)
                    .entity(new GenericResponse("Air quality notification sent to Telegram!")).build();

        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new GenericResponse(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new GenericResponse(e.getMessage())).build();
        }
    }

}
