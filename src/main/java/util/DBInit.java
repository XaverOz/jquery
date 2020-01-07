package util;

import model.Role;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DBInit implements ApplicationListener<ApplicationReadyEvent> {

    UserService userService;

    @Autowired
    @Qualifier("userServiceHibernate")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        initDB();
    }
    public void initDB() {
        User admin = new User();
        User user = new User();
        admin.setPassword("admin");
        admin.setEmail("admin@yandex.ru");
        admin.setLogin("admin");
        user.setPassword("1234");
        user.setEmail("user@yandex.ru");
        user.setLogin("user");
        Role roleAdmin = new Role("ADMIN");
        Role roleUser = new Role("USER");
        Role roleTest = new Role("TEST");
        List<Role> roles = new ArrayList<>();
        roles.add(roleUser);
        user.setRoles(roles);
        userService.addUser(user);
        roles.add(roleAdmin);
        admin.setRoles(roles);
        userService.addUser(admin);
        userService.addRole(roleTest);
    }
}
