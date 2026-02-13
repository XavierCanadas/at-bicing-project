package xaviercanadas.bicingproject.client;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import xaviercanadas.bicingproject.model.bicing.Data;

public class BicingClient {
    public static void main(String[] args) throws Exception {

        String bicingUrl = "https://opendata-ajuntament.barcelona.cat/";
        String bicingPath = "data/dataset/6aa3416d-ce1a-494d-861b-7bd07f069600/resource/1b215493-9e63-4a12-8980-2d7e0fa19f85/download";

        String bicinToken = System.getenv("TOKEN_BCN");

        Client client = ClientBuilder.newClient();
        WebTarget bicingTarget = client.target(bicingUrl).path(bicingPath);

        Data data = bicingTarget.request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", bicinToken)
                .get(new GenericType<>(){});

        System.out.println(data.toString());

    }
}
