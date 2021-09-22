package org.itechart.repository;

import org.itechart.constant.Constant;
import org.itechart.entity.Car;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.itechart.util.AbstractConnection.getConnection;

public class CarRepository implements ICarRepository{
    @Override
    public boolean add(Car car) {
        try (PreparedStatement stmt = getConnection()
                .prepareStatement(Constant.CAR_ADD)) {
            stmt.setInt(1, car.getId());
            stmt.setString(2, car.getMake());
            stmt.setString(3, car.getModel());
            stmt.setString(4, car.getBodyType());
            stmt.setString(5, car.getColor());
            stmt.setDate(6, car.getProductionDate());
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e){
            e.getMessage();
            return false;
        }
        return false;
    }

    @Override
    public Car read(int id) {
        Car car = new Car();
        try (PreparedStatement stmt = getConnection()
                .prepareStatement(Constant.CAR_READ)) {
            stmt.setInt(1, id);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            rs.next();
            car.setId(rs.getInt("id"));
            car.setMake(rs.getString("make"));
            car.setModel(rs.getString("model"));
            car.setBodyType(rs.getString("body_type"));
            car.setColor(rs.getString("color"));
            car.setProductionDate(rs.getDate("production_date"));
            rs.close();
        } catch (SQLException e) {
            e.getMessage();
        }
        return car;
    }

    @Override
    public List<Car> readAll() {
        List<Car> list = new ArrayList<>();
        try (PreparedStatement stmt = getConnection().prepareStatement(Constant.CAR_READ_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Car car = new Car(rs.getInt("id"), rs.getString("make")
                        , rs.getString("model")
                        , rs.getString("body_type"), rs.getString("color")
                        , rs.getDate("production_date"));
                list.add(car);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return list;
    }

    @Override
    public boolean update(Car car, int id) {
        try (PreparedStatement stmt = getConnection().prepareStatement(Constant.CAR_UPDATE)) {
            stmt.setString(1, car.getMake());
            stmt.setString(2, car.getModel());
            stmt.setString(3, car.getBodyType());
            stmt.setString(4, car.getColor());
            stmt.setDate(5, car.getProductionDate());
            stmt.setInt(6, id);
            stmt.execute();
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException | NumberFormatException e) {
            e.getMessage();
            return false;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (PreparedStatement stmt = getConnection().prepareStatement(Constant.CAR_DELETE)) {
            stmt.setInt(1, id);
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.getMessage();
            return false;
        }
        return false;
    }
}
