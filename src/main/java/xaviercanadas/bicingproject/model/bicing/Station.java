package xaviercanadas.bicingproject.model.bicing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import xaviercanadas.bicingproject.model.TelegramMessage;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Station(
        int station_id,
        int num_bikes_available,
        int num_docks_available,
        long last_reported,
        boolean is_charging_station,
        String status
) {

    @Override
    @NotNull
    public String toString() {
        return "{" +
                "station_id: " + station_id +
                ", num_bikes_available: " + num_bikes_available +
                ", num_docks_available: " + num_docks_available +
                ", last_reported: " + last_reported +
                ", is_charging_station: " + is_charging_station +
                ", status: " + status + '\'' +
                '}';
    }

    // Helper to
    public String toMessage() {
        return "Station " + station_id + "\n" +
                "  - Available bikes: " + num_bikes_available + "\n" +
                "  - Free docks: " + num_docks_available + "\n" +
                "  - Last update: " + last_reported + "\n" +
                "  - Charging station?: " + (is_charging_station ? "Yes" : "No") + "\n" +
                "  - Status: " + status;
    }
}
