package xaviercanadas.bicingproject.client;

import xaviercanadas.bicingproject.model.TelegramMessage;
import xaviercanadas.bicingproject.service.TelegramService;

public class TelegramClient {
    public static void main(String[] args) {

        // This client is to test that the send message works
        // Add the token and chat id as environment variable

        String telegramToken = System.getenv("TOKEN_TELEGRAM");
        String telegramChatId = System.getenv("CHAT_ID_TELEGRAM");
        TelegramMessage message = new TelegramMessage(Long.parseLong(telegramChatId), "Hello from TelegramClient!");
        TelegramService.sendMessage(message, telegramToken);

    }
}
