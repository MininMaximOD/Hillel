package org.hillel.config;

import org.hillel.service.InMemoryJourneyServiceImpl;
import org.hillel.service.JourneyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {
    @Bean("InMemoryJourneyServise")
    public JourneyService getInMemoryJourneyServise(){
        return new InMemoryJourneyServiceImpl();
    }
}
