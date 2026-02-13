package xaviercanadas.bicingproject.model.telegram;

import jakarta.validation.constraints.NotNull;

public record Message(
        long chat_id,
        String text
) {
    @Override
    @NotNull
    public String toString() {
        return "{ " +
                "chat_id: " + chat_id + ",\n" +
                "text: " + text + "\n" +
                "}";
    }
}
