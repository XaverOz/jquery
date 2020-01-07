package service;

import model.Role;
import model.User;
import java.util.List;

public interface UserService {

    public Role getRoleByName(String name);

    public void addUser(User user);

    public boolean deleteUser(long id);

    public void updateUser(User user);

    public User getUserById(long id);

    public List<User> getAllUser();

    public List<Role> getAllRole();

    public User getUserByLogin(String loginZ);

    public void addRole(Role role);

}