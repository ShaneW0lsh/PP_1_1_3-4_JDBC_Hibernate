package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {
       UserService userService = new UserServiceImpl();

       userService.createUsersTable();

       User user1 = new User("Ivan", "Baranov", (byte) 19);
       User user2 = new User("Michael", "Kremlev", (byte) 18);
       User user3 = new User("Arthur", "Chernishov", (byte) 19);
       User user4 = new User("Artem", "Polykarpov", (byte) 18);

       userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
       System.out.printf("User с именем - %s добавлен в базу данных\n", user1.getName());
       userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
       System.out.printf("User с именем - %s добавлен в базу данных\n", user2.getName());
       userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
       System.out.printf("User с именем - %s добавлен в базу данных\n", user3.getName());
       userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
       System.out.printf("User с именем - %s добавлен в базу данных\n", user4.getName());

       userService.removeUserById(3);

       List<User> listOfUsers = userService.getAllUsers();
       System.out.println(listOfUsers);

       userService.cleanUsersTable();
       userService.dropUsersTable();
    }

}
