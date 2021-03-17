package org.hillel.repository;

import org.hillel.Journey;
import org.hillel.control.ConnectionFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JourneyRepositoryImpl implements GenericRepository<Journey> {
    private final ConnectionFactory connectionFactory;
    private Properties properties = new Properties();
    private static Logger logger = Logger.getLogger(ConnectionFactory.class.getName());

    public JourneyRepositoryImpl(String pathFileConnection, String pathName) {
        connectionFactory = new ConnectionFactory(pathFileConnection);
        try {
            properties.load(new FileReader(pathName));
        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "Exeption found file: ", e);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Exeption file: ", e);
        }

    }

    @Override
    public Integer create(Journey journey) {
        Integer result = null;
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(properties.getProperty("journey_insert"));) {
            statement.setString(1, journey.getStationFrom());
            statement.setString(2, journey.getStationTo());
            statement.setDate(3, journey.getDeparture());
            statement.setDate(4, journey.getArrival());

            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Exeption SQL: ", e);
        }
        return result;

    }

    @Override
    public List<Journey> get(String stationFrom, String stationTo, LocalDateTime departure) {
        List<Journey> journeys= new ArrayList<>();
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(properties.getProperty("journey_get"));) {
            statement.setString(1, stationFrom);
            statement.setString(2, stationTo);
            statement.setDate(3, departure);

            final ResultSet rs = statement.executeQuery();
            while (rs.next()){
                LocalDateTime arrival = rs.getDate("journey_arrival");
                Journey journey = new Journey(stationFrom, stationTo, departure, arrival);
                journeys.add(journey);
            }
            return journeys;
        } catch (SQLException | IllegalAccessException e) {
            logger.log(Level.WARNING, "Exeption SQL: ", e);
        }
        return null;
    }

    @Override
    public Integer update(Journey journey) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(properties.getProperty("journey_update"));) {
            statement.setString(1, journey.getStationFrom());
            statement.setString(2, journey.getStationTo());
            statement.setDate(3, journey.getDeparture());
            statement.setDate(4, journey.getArrival());

            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Exeption SQL: ", e);
        }
        return null;

    }

    @Override
    public boolean delete(Journey journey) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(properties.getProperty("journey_update"));) {
            statement.setString(1, journey.getStationFrom());
            statement.setString(2, journey.getStationTo());
            statement.setDate(3, journey.getDeparture());
            statement.setDate(4, journey.getArrival());

            return statement.executeQuery().next();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Exeption SQL: ", e);
        }
        return false;
    }
}
