package xaviercanadas.bicingproject;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class BanApplication extends ResourceConfig {
    public BanApplication() {
        packages("xaviercanadas.bicingproject");
    }
}