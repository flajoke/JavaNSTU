package Classes;

import Classes.Objects.chick;
import Classes.Objects.bird;

import java.sql.*;
import java.util.LinkedList;

public class SQL {
    private String NAME_USER = "root";
    private String PASSWORD = "root";
    private String URL = "jdbc:mysql://127.0.0.1:3306/chick";
    Connection connection;
    public boolean act;
    public boolean act2;

    public void save(LinkedList<bird> list1, LinkedList<chick> list2) {
        connect();
        try {
            Statement truncateStatement = connection.createStatement();
            String truncateQuery = "TRUNCATE TABLE chick;";
            truncateStatement.executeUpdate(truncateQuery);

            String command = "INSERT INTO chick (id, type, x, y) values(";
            Statement statement = connection.prepareStatement(command);

            for(int i = 0; i < 50; i++) {
                command = "INSERT INTO chick (id, type, x, y) values(";
                if(list1.get(i).activeObject == true) {
                    command += list1.get(i).id + ", 1, " + list1.get(i).GetX() + ", " + list1.get(i).GetY() + ");";
                    statement.executeUpdate(command);
                }
                if(list2.get(i).activeObject == true) {
                    command += list2.get(i).id + ", 2, " + list2.get(i).GetX() + ", " + list2.get(i).GetY() + ");";
                    statement.executeUpdate(command);
                }
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        Discondect();
        act = false;
    }


    public void connect() {     // Объявление метода connect
        try {
            connection = DriverManager.getConnection(URL, NAME_USER, PASSWORD); // Установка соединения с базой данных с помощью DriverManager (класс драйверов для соединения с БД)
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public void load(LinkedList<bird> list1, LinkedList<chick> list2) {
        connect();
        String command = "SELECT * FROM chick";                     // Объявление строки command с SQL-командой для выборки данных из таблицы chick
        try {
            Statement statement = connection.createStatement();     // Для выполнения простых запросов без параметров
            ResultSet resultSet = statement.executeQuery(command);  // Выполнение SQL-команды для выборки данных из таблицы chick и сохранение результатов в resultSet
            while (resultSet.next()) {
                if(resultSet.getInt("type") == 1) {     // Если значение столбца "type" равно 1
                    list1.get(resultSet.getInt("id")).id = resultSet.getInt("id");
                    list1.get(resultSet.getInt("id")).activeObject = true;
                    list1.get(resultSet.getInt("id")).x = resultSet.getInt("x");
                    list1.get(resultSet.getInt("id")).y = resultSet.getInt("y");
                    list1.get(0).ValueObject++; // Увеличение значения переменной ValueObject объекта list1
                    System.out.println("loaded");
                }
                else if(resultSet.getInt("type") == 2) { // Если значение столбца "type" равно 2
                    list2.get(resultSet.getInt("id")).id = resultSet.getInt("id");
                    list2.get(resultSet.getInt("id")).activeObject = true;
                    list2.get(resultSet.getInt("id")).x = resultSet.getInt("x");
                    list2.get(resultSet.getInt("id")).y = resultSet.getInt("y");
                    list2.get(0).ValueObject++; // Увеличение значения переменной ValueObject объекта list2
                }
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        Discondect();
        act2 = false;
    }

    private void Discondect() { // Объявление приватного метода Discondect
        try {
            connection.close();             // Закрытие соединения с базой данных
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
}
