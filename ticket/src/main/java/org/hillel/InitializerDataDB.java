package org.hillel;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.entity.enums.VehicleType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class InitializerDataDB {

    private List<String> listCities = new ArrayList<String>(List.of(
        "Odessa", "Kiev", "Lvov", "Kharkov", "Dnepr", "Chernigov","Bucovel", "Uman'","Zaporozh'ye", "Nikolaev",
            "Ternopol", "Uzgorod", "Ivano-Francovsk", "Herson"));

    public Collection<VehicleEntity> setContentVehicleTab(){
        List<VehicleEntity> vehicles = new ArrayList<VehicleEntity>();
        for (int i = 0; i < 12; i++) {
            VehicleEntity vehicleEntity = new VehicleEntity();
            vehicleEntity.setName("Bus" + i);
            vehicleEntity.setVehicleType(VehicleType.BUS);
            vehicleEntity.setCountSeats(generateRandomInteger(24, 48));
            vehicleEntity.setOrderNumber(generateOrderNumber(i));
            vehicleEntity.setActive(generateRandomBoolean());
            vehicles.add(vehicleEntity);
            System.out.println(vehicleEntity);
        }
        return vehicles;
    }

    public Collection<JourneyEntity> setContentJourneyTab(){
        List<JourneyEntity> journeys = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            JourneyEntity journey = new JourneyEntity();
            String stationFrom = generateCity();
            String stationTo = generateCity();
            Instant dateFrom = Instant.now().plusSeconds(generateRandomInteger(10, 100)*1000L);
            Instant dateTo = dateFrom.plusSeconds(generateRandomInteger(10, 40) * 1000L);
            Boolean flag = true;

            while (stationFrom.equals(stationTo)) {
                stationTo = generateCity();
            }
            String name = stationFrom + " - " + stationTo;
            journey.setStationFrom(stationFrom);
            journey.setStationTo(stationTo);
            journey.setName(name);
            journey.setDateFrom(dateFrom);
            journey.setDateTo(dateTo);
            journey.setActive(true);
            journeys.add(journey);
        }
        return journeys;
    }

    public Collection<StopEntity> setContentStopTab(){
        List<StopEntity> stops = new ArrayList<>();
        for(int i = 0; i < generateRandomInteger(5, 10); i++){
            StopEntity stop = new StopEntity();
            stop.setName("stop_" + i);
            stops.add(stop);
        }
        return stops;
    }

    private String generateOrderNumber(int i){
        StringBuilder result = new StringBuilder();
        result.append(i)
                .reverse()
                .append("000")
            .setLength(4);
        result.append("AB")
            .reverse();
        return result.toString();
    }

    private String generateCity(){
        return listCities.get(generateRandomInteger(0, listCities.size() - 1));
    }

    private int generateRandomInteger(int min, int max) {
        return (int)(Math.random() * (max - min) + min);
    }

    private boolean generateRandomBoolean(){
        Random result = new Random();
        return result.nextBoolean();
    }
}
