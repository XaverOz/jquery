package util;

import model.Role;
import model.User;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component
public class MyDBConfiguration extends Configuration {

    public MyDBConfiguration() {
        super();
        addAnnotatedClass(User.class);
        addAnnotatedClass(Role.class);
        setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/pre2");
        setProperty("hibernate.connection.username", "zaa");
        setProperty("hibernate.connection.password", "Control1");
        setProperty("hibernate.show_sql", "true");
        setProperty("hibernate.hbm2ddl.auto", "create");
    }

}