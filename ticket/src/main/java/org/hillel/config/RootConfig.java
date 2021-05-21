package org.hillel.config;

import org.hillel.service.TicketClient;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("org.hillel")
@PropertySource({"database.properties"})
public class RootConfig {

    @Bean
    public TicketClient ticketClient(){
        return new TicketClient();
    }
}
