package org.hillel;

import org.hillel.config.RootConfig;
import org.hillel.persistence.entity.AbstractModifyEntity;
import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.service.TicketClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Stream;

public class Starter {
    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        // AppContex.load("application.properties");
        //   final JourneyService journeyService = AppContex.getBean("journeyService");
//        final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("common-beans.xml");
        final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(RootConfig.class); //создание базы бинов через BeanFactory

        //  final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(RootConfig.class);
        TicketClient ticketClient = applicationContext.getBean(TicketClient.class);

        //Заполнение базы данными
        System.out.println("Initialize testing data");
        InitializerDataDB dataDB = new InitializerDataDB();

        Collection<JourneyEntity> journeys = (dataDB.setContentJourneyTab());
        System.out.println("insert journeys to DB");
        for (JourneyEntity journey : journeys) {
//            System.out.println(journey);
            ticketClient.createOrUpdateJourney(journey);
        }

        System.out.println("insert vehicles to DB");
        Collection<VehicleEntity> vehicles = dataDB.setContentVehicleTab();
        for (VehicleEntity vehicle : vehicles) {
            System.out.println(vehicle);
            ticketClient.createOrUpdateVehicle(vehicle);
        }

        System.out.println("insert stops to DB");
        Collection<StopEntity> stops = dataDB.setContentStopTab();
        for (StopEntity stop : stops) {
            ticketClient.createOrUpdateStop(stop);
        }
        System.out.println("Finish initialize data for DB");

        //Д.з. 6 основное задание
        System.out.println("_______________________________");
        int countValues = 5;
        int countPagesJourneys = (ticketClient.getCountJourneys()).intValue() / countValues;
        int countPagesVehicles = (ticketClient.getCountVehicles()).intValue() / countValues;
        int countPagesStops = (ticketClient.getCountStops()).intValue() / countValues;

        Scanner scanner = new Scanner(System.in);
        System.out.println("enter entity");
        String entity = scanner.next();
        System.out.println("enter param for sorting");
        String sortsField = scanner.next();
        int numberPage;

        switch (entity){
            case "journey":
                System.out.println(entity + " include " + countPagesJourneys + " pages. Enter number page.");
                numberPage = scanner.nextInt();
                Stream<JourneyEntity> streamJourney = (ticketClient.findAllJourneys(numberPage, countValues, sortsField, true)).stream();
                streamJourney.forEach(journeyEntity -> System.out.println(journeyEntity));
                  break;
            case "vehicle":
                System.out.println(entity + " include " + countPagesVehicles + " pages. Enter number page.");
                numberPage = scanner.nextInt();
                Stream<VehicleEntity> streamVehicle = (ticketClient.findAllVehicles(numberPage, countValues, sortsField, true)).stream();
                streamVehicle.forEach(vehiclesEntity -> System.out.println(vehiclesEntity));
            //  System.out.println(ticketClient.findAllVehicles(numberPage, countValues, sortsField, true));
                break;
            case "stop":
                System.out.println(entity + " include " + countPagesStops + " pages. Enter number page.");
                numberPage = scanner.nextInt();
                Stream<StopEntity> streamStop = (ticketClient.findAllStops(numberPage, countValues, sortsField, true)).stream();
                streamStop.forEach(journeyStop -> System.out.println(journeyStop));
           //   System.out.println(ticketClient.findAllStops(numberPage, countValues, sortsField, true));
                break;
            default:
                System.out.println(entity + " not found");
                break;
        }

        //Д.з. 6 задаение со *
        System.out.println("result with max count seats");
        Collection<VehicleEntity> vehicleEntities = ticketClient.findVehiclesWithMaxOrMinFreeSeats("max");
//        System.out.println(vehicleEntities);
//        System.out.println(ticketClient.findVehiclesWithMaxOrMinFreeSeats("max"));
        Stream<VehicleEntity> stream = (ticketClient.findVehiclesWithMaxOrMinFreeSeats("max")).stream();
        stream.forEach(vehicleEntity -> System.out.println(vehicleEntity));
        System.out.println("------------------------------------");
        System.out.println("result with min count seats");
        Stream<VehicleEntity> stream2 = (ticketClient.findVehiclesWithMaxOrMinFreeSeats("min")).stream();
        stream2.forEach(vehicleEntity -> System.out.println(vehicleEntity));

        }


     //   System.out.println("add vehicle to journey");
//        addVehicleOnJourney(ticketClient);




    /*public static void addVehicleOnJourney(TicketClient ticketClient){
        Collections<JourneyEntity> journeys = ticketClient.findAllJourneys();

        int count = generateRandomInteger(10, journeys.)
        for(int i; i < )

    }*/

    private int generateRandomInteger(int min, int max) {
        return (int)Math.random() * (max - min) + min;
    }
}
