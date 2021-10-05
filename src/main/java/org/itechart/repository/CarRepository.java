package org.itechart.repository;

import org.itechart.constant.Constant;
import org.itechart.entity.Car;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.itechart.util.AbstractConnection.getConnection;
import static org.itechart.util.Log.logger;

public class CarRepository implements ICarRepository{

    @Override
    public Car add(Car car) throws SQLException {
        try (PreparedStatement stmt = getConnection()
                .prepareStatement(Constant.CAR_ADD)) {
            stmt.setObject(1, car.getUuid());
            stmt.setString(2, car.getMake());
            stmt.setString(3, car.getModel());
            stmt.setString(4, car.getBodyType());
            stmt.setString(5, car.getColor());
            stmt.setDate(6, car.getProductionDate());
            stmt.execute();

            //Нужно ли обращатьс к базе для получения сохраненного объекта?
        } catch (SQLException e){
            logger.error(e.getMessage());
            throw e;
        }
        return car;
    }

    @Override
    public Car read(UUID uuid) throws SQLException {
        Car car = new Car();
        try (PreparedStatement stmt = getConnection()
                .prepareStatement(Constant.CAR_READ)) {
            stmt.setObject(1,uuid);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            rs.next();
            car.setId(rs.getInt("id"));
            car.setMake(rs.getString("make"));
            car.setModel(rs.getString("model"));
            car.setBodyType(rs.getString("body_type"));
            car.setColor(rs.getString("color"));
            car.setProductionDate(rs.getDate("production_date"));
            car.setUuid(UUID.fromString(rs.getString("uuid")));
            rs.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw e;
        }
        return car;
    }

    @Override
    public List<Car> readAll() throws SQLException{
        List<Car> list = new ArrayList<>();
        try (PreparedStatement stmt = getConnection().prepareStatement(Constant.CAR_READ_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Car car = new Car(rs.getInt("id"), UUID.fromString(rs.getString("uuid"))
                        , rs.getString("make")
                        , rs.getString("model")
                        , rs.getString("body_type"), rs.getString("color")
                        , rs.getDate("production_date"));
                list.add(car);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw e;
        }
        return list;
    }

    @Override
    public Car update(Car car) throws SQLException{
        try (PreparedStatement stmt = getConnection().prepareStatement(Constant.CAR_UPDATE)) {
            stmt.setString(1, car.getMake());
            stmt.setString(2, car.getModel());
            stmt.setString(3, car.getBodyType());
            stmt.setString(4, car.getColor());
            stmt.setDate(5, car.getProductionDate());
            stmt.setObject(6, car.getUuid());
            stmt.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw e;
        }
        return car;
    }

    @Override
    public boolean delete(UUID uuid) throws SQLException{
        try (PreparedStatement stmt = getConnection().prepareStatement(Constant.CAR_DELETE)) {
            stmt.setObject(1, uuid);
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw e;
        }
        return false;
    }
}
