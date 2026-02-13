package xaviercanadas.bicingproject.model;

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
