package xaviercanadas.bicingproject.service;

import jakarta.ws.rs.NotFoundException;
import org.apache.log4j.Logger;
import xaviercanadas.bicingproject.model.Client;
import xaviercanadas.bicingproject.model.TelegramMessage;
import xaviercanadas.bicingproject.model.bicing.Station;
import xaviercanadas.bicingproject.registry.ClientRegistry;
import xaviercanadas.bicingproject.resources.ClientResource;

import java.util.List;

public class NotifierService {

    private final static Logger logger = Logger.getLogger(NotifierService.class);


    public static void notifySlots(String phoneNumber) throws NotFoundException {
        ClientRegistry registry = ClientRegistry.getInstance();
        Client client =  registry.getClient(phoneNumber);

        if (client == null) {
            logger.error("The client is not subscribed to any station");
            throw new NotFoundException("The client is not subscribed to any station");
        }

        List<Station> stations = StationService.getStationsByIds(client.getStationIds());

        StringBuilder messageText = new StringBuilder("The current state of the subscribed stations is:\n\n");
        for (Station station : stations) {
            messageText.append(station.toMessage());
            messageText.append("\n");
        }

        TelegramMessage message = new TelegramMessage(client.getTelegramChatId(),  messageText.toString());
        TelegramService.sendMessage(message, client.getTelegramToken());
    }

    public static void notifyAirQuality(String phoneNumber, String ip) throws NotFoundException {
        ClientRegistry registry = ClientRegistry.getInstance();
        Client client = registry.getClient(phoneNumber);

        if (client == null) {
            throw new NotFoundException("The user is not registered");
        }

        // Obtain city from IP
        String city = AirQualityService.getCityFromIp(ip);
        
        // Obtain air quality info for the city
        String airQualityInfo = AirQualityService.getAirQuality(city);

        // Build message and send it to the user via Telegram
        String messageText = "Detected location: " + city + "\n" +
                             "Air quality: " + airQualityInfo;

        TelegramMessage message = new TelegramMessage(client.getTelegramChatId(), messageText);
        TelegramService.sendMessage(message, client.getTelegramToken());
    }
}
