package xaviercanadas.bicingproject.service;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import xaviercanadas.bicingproject.model.TelegramMessage;

public class TelegramService {

    private final static Logger logger = Logger.getLogger(TelegramService.class);

    // URLs
    private static final String TELEGRAM_API_URL = "https://api.telegram.org";


    public static void sendMessage(TelegramMessage message, String token) {
        try {
            String url = TELEGRAM_API_URL + "/bot" + token + "/sendMessage";

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(url);
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(message));

            if (response.getStatus() == 200) {
                logger.info("Message sent successfully to chat ID: " + message.chat_id());
            } else {
                logger.error("Failed to send message. Status: " + response.getStatus() + ", Reason: " + response.readEntity(String.class));
            }
        } catch (Exception e) {
            logger.error("Exception while sending message to Telegram: ", e);
        }
    }
}
