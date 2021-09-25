package org.itechart.constant;

public class Constant {
    public static final String CAR_READ = "SELECT * FROM data.cars WHERE id=?";
    public static final String CAR_READ_ALL = "SELECT * FROM data.cars";
    public static final String CAR_ADD = "INSERT INTO data.cars (uuid, make, model, body_type, color," +
            " production_date) VALUES (?,?,?,?,?,?)";
    public static final String CAR_UPDATE = "UPDATE data.cars SET make=?, model=?, body_type=?," +
            " color=?, production_date=? WHERE id=?";
    public static final String CAR_DELETE = "DELETE FROM data.cars WHERE id=?";
}
