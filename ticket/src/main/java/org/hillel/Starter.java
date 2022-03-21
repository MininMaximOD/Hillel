package org.hillel;

import org.hillel.service.JourneyService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.time.LocalDateTime;

public class Starter {
    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
       // AppContex.load("application.properties");
     //   final JourneyService journeyService = AppContex.getBean("journeyService");
        final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("common-beans.xml");
        final JourneyService journeyService = applicationContext.getBean(JourneyService.class);

        System.out.println(journeyService.find("Odessa", "Kiev", LocalDateTime.now()));
    }
}
