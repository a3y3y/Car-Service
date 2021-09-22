package org.itechart.constant;

public class Constant {
    public static final String CAR_READ = "SELECT * FROM cars WHERE id=?";
    public static final String CAR_READ_ALL = "SELECT * FROM cars";
    public static final String CAR_ADD = "INSERT INTO cars (id, make, model, body_type, color," +
            " production_date) VALUES (?,?,?,?,?,?)";
    public static final String CAR_UPDATE = "UPDATE cars SET make=?, model=?, body_type=?," +
            " color=?, production_date=? WHERE id=?";
    public static final String CAR_DELETE = "DELETE FROM cars WHERE id=?";
}
