package xaviercanadas.bicingproject.dto;

import jakarta.validation.constraints.NotNull;

public record GenericResponse(
        String message
) {
        @Override
        @NotNull
        public String toString() {
            return "{ " +
                    "message: " + message + "\n" +
                    "}";
        }
}
