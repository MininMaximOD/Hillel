package org.hillel;

import org.hillel.config.RootConfig;
import org.hillel.persistence.entity.*;
import org.hillel.service.TicketClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
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
        Long countPagesJourneys = (ticketClient.getCountJourneys()) / countValues;
        Long countPagesVehicles = (ticketClient.getCountVehicles()) / countValues;
        Long countPagesStops = (ticketClient.getCountStops()) / countValues;

        System.out.println(countPagesJourneys+" -=- "+countPagesVehicles+" -=- "+countPagesStops );

        Stream<JourneyEntity> streamJourneyPagenationAndSort = (ticketClient.findAllJourneysWithPaginationAndSort(1, countValues, JourneyEntity_.NAME)).stream();
        streamJourneyPagenationAndSort.forEach(journeyEntity -> System.out.println(journeyEntity));

        Stream<JourneyEntity> streamJourneyPagenation = (ticketClient.findAllJourneysWithPagination(1, countValues)).stream();
        streamJourneyPagenation.forEach(journeyEntity -> System.out.println(journeyEntity));

        Stream<VehicleEntity> streamVehiclePagenationAndSort = (ticketClient.findAllVehiclesWithPaginationAndSort(1, countValues, VehicleEntity_.NAME)).stream();
        streamVehiclePagenationAndSort.forEach(vehiclesEntity -> System.out.println(vehiclesEntity));

        Stream<VehicleEntity> streamVehiclePagenation = (ticketClient.findAllVehiclesWithPagination(1, countValues)).stream();
        streamVehiclePagenation.forEach(vehiclesEntity -> System.out.println(vehiclesEntity));

        Stream<StopEntity> streamStopPagenation = (ticketClient.findAllWithStopsPaginationAndSort(0, countValues, StopEntity_.NAME)).stream();
        streamStopPagenation.forEach(stopEntity -> System.out.println(stopEntity));

        Stream<StopEntity> streamStopPagenationAndSort = (ticketClient.findAllStopsWithPagination(1, countValues)).stream();
        streamStopPagenationAndSort.forEach(stopEntity -> System.out.println(stopEntity));

        System.out.println("-------------------------------------");
        System.out.println("Dynamic filter");

        JourneyEntity journey = new JourneyEntity();
        journey.setStationTo("Kiev");
        journey.setStationFrom("Odessa");
        journey.setActive(true);
        journey.setDateTo(Instant.now());
        journey.setDateFrom(Instant.now());
        ticketClient.createOrUpdateJourney(journey);
        HashMap<String, String> predicates = new HashMap<>();
        predicates.put("journey.stationFrom", "Odessa");
        predicates.put("journey.stationTo", "Kiev");
        Stream<JourneyEntity> streamWithMapPredicates = (ticketClient.findAllWithDynamicFilterJourney(predicates, 0,5)).stream();
        streamWithMapPredicates.forEach(journeyEntity -> System.out.println(journeyEntity));

        HashMap<String, String> predicatesVehicle = new HashMap<>();
        predicatesVehicle.put("vehicle.countSeats", "34");
        Stream<VehicleEntity> streamWithMapPredicatesVehicle = (ticketClient.findAllWithDynamicFilterVehicle(predicatesVehicle, 0,5)).stream();
        streamWithMapPredicatesVehicle.forEach(vehicleEntity -> System.out.println(vehicleEntity));

        HashMap<String, String> predicatesStop = new HashMap<>();
        predicatesStop.put("stop.name", "stop_1");
        Stream<StopEntity> streamWithMapPredicatesStop = (ticketClient.findAllWithDynamicFilterStop(predicatesStop, 0,5)).stream();
        streamWithMapPredicatesStop.forEach(stopEntity -> System.out.println(stopEntity));
       /* Scanner scanner = new Scanner(System.in);
        System.out.println("enter entity");
        String entity = scanner.next();
        System.out.println("enter param for sorting");
        String sortsField = scanner.next();
        int numberPage;

        switch (entity){
            case "journey":
                System.out.println(entity + " include " + countPagesJourneys + " pages. Enter number page.");
                numberPage = scanner.nextInt();
                Stream<JourneyEntity> streamJourneyPagenationAndSort = (ticketClient.findAllJourneys(numberPage, countValues, sortsField, true)).stream();
                streamJourneyPagenationAndSort.forEach(journeyEntity -> System.out.println(journeyEntity));
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
        }*/

       /* //Д.з. 6 задаение со *
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
*/
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
