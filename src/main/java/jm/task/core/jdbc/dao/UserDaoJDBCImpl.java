package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            Statement statement = connection.createStatement();
        statement.executeUpdate("""
            CREATE TABLE Users(
                id INTEGER NOT NULL AUTO_INCREMENT,
                name VARCHAR(30),
                last_name VARCHAR(30),
                age INTEGER,
                PRIMARY KEY (id)
            )
        """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("""
                DROP TABLE Users;
            """);
        } catch (SQLSyntaxErrorException e) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            String sql = "INSERT INTO Users (name, last_name, age) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            String sql = "DELETE FROM Users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Users";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result.add(new User(
                    resultSet.getString("name"),
                    resultSet.getString("last_name"),
                    resultSet.getByte("age")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void cleanUsersTable() {
        try {
            String sql = "DELETE FROM Users";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
