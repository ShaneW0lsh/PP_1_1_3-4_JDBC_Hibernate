package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactoryHibernate();
    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            String sql = """
                CREATE TABLE IF NOT EXISTS User(
                    id INTEGER NOT NULL AUTO_INCREMENT,
                    name VARCHAR(30),
                    lastName VARCHAR(30),
                    age INTEGER,
                    PRIMARY KEY (id)
                )
            """;
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            String sql = """
                DROP TABLE IF EXISTS User;
            """;
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            result = session.createNativeQuery("SELECT * FROM User", User.class).getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createNativeQuery("DELETE FROM User", User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }
}
