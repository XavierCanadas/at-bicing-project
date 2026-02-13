package xaviercanadas.bicingproject.model.bicing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Data(
        Stations data
) {
    @Override
    public String toString() {
        return "{" +
                "data: " + data.toString() +
                "}";
    }
}
