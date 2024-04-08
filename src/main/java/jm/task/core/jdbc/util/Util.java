package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String userName = "root";
    private static final String password = "root";
    private static final String connectionUrl = "jdbc:mysql://localhost:3306/KataUsersDB";
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(connectionUrl, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactoryHibernate() {
        if (sessionFactory == null) {
            try {
                Configuration config = getConfiguration();
                config.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties()).build();

                sessionFactory = config.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.out.println("Problem creating session factory!");
                System.out.println(e.getMessage());
            }
        }
        return sessionFactory;
    }

    // dopisat'
    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    private static Configuration getConfiguration() {
        Configuration config = new Configuration();
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, driver);
        settings.put(Environment.URL, connectionUrl);
        settings.put(Environment.USER, userName);
        settings.put(Environment.PASS, password);
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.HBM2DDL_AUTO, "create-drop");

        config.setProperties(settings);
        return config;
    }

}
