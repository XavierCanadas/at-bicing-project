package xaviercanadas.bicingproject.model;

import java.util.List;

public class Client {
    private String name;
    private String phoneNumber;
    private String telegramToken;
    private long telegramChatId;
    private List<Integer> stationIds;

    public Client() {}

    public Client(String name, String phoneNumber, String telegramToken, long telegramChatId, List<Integer> stationIds) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.telegramToken = telegramToken;
        this.telegramChatId = telegramChatId;
        this.stationIds = stationIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTelegramToken() {
        return telegramToken;
    }

    public void setTelegramToken(String telegramToken) {
        this.telegramToken = telegramToken;
    }

    public long getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(long telegramChatId) {
        this.telegramChatId = telegramChatId;
    }

    public List<Integer> getStationIds() {
        return stationIds;
    }

    public void setStationIds(List<Integer> stationIds) {
        this.stationIds = stationIds;
    }

    @Override
    public String toString() {
        return "{" +
                "phoneNumber: " + phoneNumber + '\n' +
                ", telegramToken: " + telegramToken + '\n' +
                ", telegramChatId: " + telegramChatId + '\n' +
                ", stationIds: " + stationIds + '\n' +
                '}';
    }
}
