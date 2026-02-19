package xaviercanadas.bicingproject.registry;

import xaviercanadas.bicingproject.model.Client;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientRegistry {
    private static ClientRegistry instance;
    private final Map<String, Client> clients = new HashMap<>();

    private ClientRegistry() {}

    // singleton
    public static synchronized ClientRegistry getInstance() {
        if (instance == null) instance = new ClientRegistry();
        return instance;
    }

    public void addClient(Client c) {
        clients.put(c.getPhoneNumber(), c);
    }

    public Client getClient(String phone) {
        return clients.get(phone);
    }

    public List<Client> getAllClients() {
        return clients.values().stream().toList();
    }

    public boolean exists(String phone) {
        return clients.containsKey(phone);
    }
}
