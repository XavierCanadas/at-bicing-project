package xaviercanadas.bicingproject.model;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record Stations(
        List<Station> stations
) {
    @Override
    @NotNull
    public String toString() {
        // Fa falta?
        StringBuilder stationsString = new StringBuilder("[");
        for (Station station : stations) {
            stationsString.append(station.toString()).append("\n");
        }
        return stationsString.append("]").toString();
    }
}
