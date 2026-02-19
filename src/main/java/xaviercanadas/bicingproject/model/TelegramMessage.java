package xaviercanadas.bicingproject.model;

import jakarta.validation.constraints.NotNull;

public record TelegramMessage(
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
