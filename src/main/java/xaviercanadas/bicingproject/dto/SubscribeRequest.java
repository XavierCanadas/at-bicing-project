package xaviercanadas.bicingproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SubscribeRequest(
        @JsonProperty("phone") String phone,
        @JsonProperty("telegram_token") String telegramToken,
        @JsonProperty("chat_id") long chatId,
        @JsonProperty("stations_ids") List<Integer> stationsIds
) {
}
