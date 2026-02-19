package xaviercanadas.bicingproject.service;

import org.apache.log4j.Logger;
import xaviercanadas.bicingproject.dto.SubscribeRequest;
import xaviercanadas.bicingproject.model.Client;
import xaviercanadas.bicingproject.registry.ClientRegistry;

public class ClientService {

    private final static Logger logger = Logger.getLogger(ClientService.class);

    public static void addClient(SubscribeRequest request) {
        ClientRegistry registry = ClientRegistry.getInstance();

        if (registry.exists(request.phone())) {
            logger.warn("Client already exists, updating subscription: " + request.phone());
        }

        Client client = new Client(
                request.phone(),
                request.telegramToken(),
                request.chatId(),
                request.stationsIds()
        );

        registry.addClient(client);
        logger.info("Client registered: " + client);
    }
}
