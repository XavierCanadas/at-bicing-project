package xaviercanadas.bicingproject.client;

import xaviercanadas.bicingproject.model.TelegramMessage;
import xaviercanadas.bicingproject.model.bicing.Station;
import xaviercanadas.bicingproject.service.TelegramService;

public class TelegramClient {
    public static void main(String[] args) {

        // This client is to test that the send message works
        // Add the token and chat id as environment variable

        String telegramToken = System.getenv("TOKEN_TELEGRAM");
        String telegramChatId = System.getenv("CHAT_ID_TELEGRAM");

        Station station = new Station(12, 43, 12, 4342, true, "a");

        TelegramMessage message = new TelegramMessage(Long.parseLong(telegramChatId), station.toMessage());
        TelegramService.sendMessage(message, telegramToken);

    }
}
