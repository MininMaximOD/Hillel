package org.hillel.config;

import org.hillel.service.JourneyService;
import org.hillel.service.JourneyServiceDB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"application.properties", "database.properties"})
public class RootConfig {
    @Bean("InMemoryJourneyServise")
    public JourneyService getInMemoryJourneyServise(){
        return new JourneyServiceDB();
    }
}
