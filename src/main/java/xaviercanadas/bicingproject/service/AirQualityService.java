package xaviercanadas.bicingproject.service;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.log4j.Logger;

public class AirQualityService {

    private final static Logger logger = Logger.getLogger(AirQualityService.class);

    private static final String IP_API_URL = "http://ip-api.com/json/";
    private static final String AIR_QUALITY_API_URL = "https://api.waqi.info/feed/";

    private static final String AIR_QUALITY_TOKEN = System.getenv("AIR_QUALITY_TOKEN");

    public static String getCityFromIp(String ip) {
        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(IP_API_URL + "/" + ip)
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            if (response.getStatus() == 200) {
                String jsonResponse = response.readEntity(String.class);
                return jsonResponse.split("\"city\":\"")[1].split("\"")[0];
            }
        } catch (Exception e) {
            logger.error("Error obtaining city by IP: " + e.getMessage());
        }
        return "Barcelona"; // Default city if something goes wrong
    }

    public static String getAirQuality(String city) {
        try {
            Client client = ClientBuilder.newClient();

            Response response = client.target(AIR_QUALITY_API_URL + city + "/?token=" + AIR_QUALITY_TOKEN)
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            if (response.getStatus() == 200) {
                String jsonResponse = response.readEntity(String.class);

                if (jsonResponse.contains("\"status\":\"ok\"") || jsonResponse.contains("\"status\": \"ok\"")) {
                    
                    // Extract AQI value from the JSON response
                    String aqi = jsonResponse.split("\"aqi\":")[1].split(",")[0].trim();
                    int aqiValue = Integer.parseInt(aqi);

                    // Extract main contaminant as extra information
                    String pollutant = jsonResponse.split("\"dominentpol\":\"")[1].split("\"")[0];

                    // Determine AQI category based on the AQI value
                    String aqiCategory = getAqiCategoryLabel(aqiValue);
                    
                    return aqiValue + " AQI\nLevel: " + aqiCategory + "\n(Main contaminant: " + pollutant.toUpperCase() + ")";
                } 
            }
        } catch (Exception e) {
            logger.error("Error obtaining air quality: " + e.getMessage());
        }
        return "Data not available";
    }

    private static String getAqiCategoryLabel(int aqi) {
        if (aqi >= 0 && aqi <= 50) {
            return "🟢 Good";
        } else if (aqi >= 51 && aqi <= 100) {
            return "🟡 Moderate";
        } else if (aqi >= 101 && aqi <= 150) {
            return "🟠 Unhealthy for Sensitive Groups";
        } else if (aqi >= 151 && aqi <= 200) {
            return "🔴 Unhealthy";
        } else if (aqi >= 201 && aqi <= 300) {
            return "🟣 Very Unhealthy";
        } else if (aqi >= 301) {
            return "🟤 Hazardous";
        } else {
            return "⚪ Unknown";
        }
    }
}