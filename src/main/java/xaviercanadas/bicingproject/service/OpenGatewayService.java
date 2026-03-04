package xaviercanadas.bicingproject.service;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.log4j.Logger;

import java.util.Base64;

public class OpenGatewayService {

    private final static Logger logger = Logger.getLogger(OpenGatewayService.class);

    // URLs
    private static final String AUTHORIZE_URL = "https://sandbox.opengateway.telefonica.com/apigateway/bc-authorize";
    private static final String TOKEN_URL = "https://sandbox.opengateway.telefonica.com/apigateway/token";
    private static final String AGE_VERIFICATION_URL = "https://sandbox.opengateway.telefonica.com/apigateway/kyc-age-verification/v0.1/verify";
    private static final String NAME_VERIFICATION_URL = "https://sandbox.opengateway.telefonica.com/apigateway/kyc-match/v0.2/match";

    private static final String CLIENT_ID_AGE = System.getenv("OPEN_GATEWAY_CLIENT_ID");
    private static final String CLIENT_SECRET_AGE = System.getenv("OPEN_GATEWAY_CLIENT_SECRET");

    private static final String CLIENT_ID_NAME = System.getenv("OPEN_GATEWAY_NAME_CLIENT_ID");
    private static final String CLIENT_SECRET_NAME = System.getenv("OPEN_GATEWAY_NAME_SECRET");

    private static final String AGE_SCOPE = "dpv:FraudPreventionAndDetection kyc-age-verification:verify";
    private static final String NAME_SCOPE = "dpv:FraudPreventionAndDetection kyc-match:match";

    private static String getBasicAuth(String Client_ID, String Client_Secret) {
        String authString = Client_ID + ":" + Client_Secret;
        return Base64.getEncoder().encodeToString(authString.getBytes());
    }

    /**
     * Ask for auth_req_id) to bc-authorize
     */
    private static String getAuthReqId(String phoneNumber, String scope, String Client_ID, String Client_Secret) {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(AUTHORIZE_URL);

            String formattedPhone = phoneNumber.startsWith("+")? phoneNumber : "+34" + phoneNumber;
            // Telephone num formatted as "tel:+34666111222" as per Open Gateway requirements
            String loginHint = "tel:" + formattedPhone;
            // Scope as per Open Gateway documentation for age verification

            Form form = new Form();
            form.param("login_hint", loginHint); 
            form.param("scope", scope); 

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + getBasicAuth(Client_ID, Client_Secret))
                    .post(Entity.form(form));

            String jsonResponse = response.readEntity(String.class);

            if (response.getStatus() == 200 || response.getStatus() == 201) {
                // Extract auth_req_id from the JSON response
                return jsonResponse.split("\"auth_req_id\":\"")[1].split("\"")[0];
            } else {
                logger.error("Error at bc-authorize (Step 1). Status: " + response.getStatus() + ", Reason: " + jsonResponse);
                return null;
            }
        } catch (Exception e) {
            logger.error("Exception obtaining auth_req_id: " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtain access token using the auth_req_id
     */
    private static String getAccessToken(String authReqId, String Client_ID, String Client_Secret) {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(TOKEN_URL);

            Form form = new Form();
            form.param("grant_type", "urn:openid:params:grant-type:ciba");
            form.param("auth_req_id", authReqId);

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + getBasicAuth(Client_ID, Client_Secret))
                    .post(Entity.form(form));

            String jsonResponse = response.readEntity(String.class);

            if (response.getStatus() == 200) {
                return jsonResponse.split("\"access_token\":\"")[1].split("\"")[0];
            } else {
                logger.error("Error at /token (Step 2). Status: " + response.getStatus() + ", Reason: " + jsonResponse);
                return null;
            }
        } catch (Exception e) {
            logger.error("Exception obtaining token: " + e.getMessage());
            return null;
        }
    }

    /**
     * Main method to check if the user is an adult by calling the Open Gateway API
     */
    public static boolean isAdult(String phoneNumber) {
        if (CLIENT_ID_AGE == null || CLIENT_SECRET_AGE == null) {
            logger.error("The Open Gateway credentials are missing in the environment variables!");
            return false;
        }

        // Obtain auth_req_id
        String authReqId = getAuthReqId(phoneNumber, AGE_SCOPE, CLIENT_ID_AGE, CLIENT_SECRET_AGE);
        if (authReqId == null) return false;

        // Obtain access token using the auth_req_id
        String token = getAccessToken(authReqId, CLIENT_ID_AGE, CLIENT_SECRET_AGE);
        if (token == null) return false;

        // Ask for age
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(AGE_VERIFICATION_URL);

            String jsonPayload = "{\"ageThreshold\": 25}";

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token)
                    .post(Entity.json(jsonPayload));

            String jsonResponse = response.readEntity(String.class);

            if (response.getStatus() == 200) {
                logger.info("Response from Age Verification: " + jsonResponse);
                return jsonResponse.contains("\"ageCheck\":\"true\"") || jsonResponse.contains("\"ageCheck\": \"true\"");
            } else {
                logger.warn("The number " + phoneNumber + " has not been verified. Status: " + response.getStatus() + ", Reason: " + jsonResponse);
                return false;
            }
        } catch (Exception e) {
            logger.error("Exception verifying age: " + e.getMessage());
            return false;
        }
    }

    public static boolean isCorrectName(String phoneNumber, String name) {
        if (CLIENT_ID_NAME == null || CLIENT_SECRET_NAME == null) {
            logger.error("The Open Gateway credentials are missing in the environment variables!");
            return false;
        }

        // Obtain auth_req_id
        String authReqId = getAuthReqId(phoneNumber, NAME_SCOPE,  CLIENT_ID_NAME, CLIENT_SECRET_NAME);
        if (authReqId == null) return false;

        // Obtain access token using the auth_req_id
        String token = getAccessToken(authReqId,  CLIENT_ID_NAME, CLIENT_SECRET_NAME);
        if (token == null) return false;

        // Ask for name
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(NAME_VERIFICATION_URL);

            String jsonPayload = String.format("{\"phoneNumber\":\"%s\", \"name\":\"%s\"}", phoneNumber, name);

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token)
                    .post(Entity.json(jsonPayload));

            String jsonResponse = response.readEntity(String.class);

            if (response.getStatus() == 200) {
                logger.info("Response from Name Verification: " + jsonResponse);
                return jsonResponse.contains("\"nameMatch\":\"true\"") || jsonResponse.contains("\"nameMatch\": \"true\"");
            } else {
                logger.warn("The number " + phoneNumber + " has not been verified. Status: " + response.getStatus() + ", Reason: " + jsonResponse);
                return false;
            }
        } catch (Exception e) {
            logger.error("Exception verifying name: " + e.getMessage());
            return false;
        }
    }
}