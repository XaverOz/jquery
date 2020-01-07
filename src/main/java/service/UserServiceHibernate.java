package service;

import model.Role;
import model.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserServiceHibernate implements UserService {

    private SessionFactory sessionFactory;

    @Autowired
    public UserServiceHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Role getRoleByName(String name) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Role where name =:name");
        query.setParameter("name", name);
        Role role = (Role) query.uniqueResult();
        session.close();
        return role;
    }

    public boolean deleteUser(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("Delete FROM User where id =:id");
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public void updateUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(user);
        user.getRoles().stream().filter(a->session.get(a.getClass(), a.getId()) != null).forEach(a->session.merge(a));
        user.getRoles().stream().filter(a->session.get(a.getClass(), a.getId()) == null).forEach(a->session.save(a));
        session.getTransaction().commit();
        session.close();
    }

    public void addUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        user.getRoles().stream().filter(a->session.get(a.getClass(), a.getId()) != null).forEach(a->session.merge(a));
        user.getRoles().stream().filter(a->session.get(a.getClass(), a.getId()) == null).forEach(a->session.save(a));
        session.getTransaction().commit();
        session.close();
    }

    public User getUserById(long id) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM User where id =:id");
        query.setParameter("id", id);
        User user = (User) query.uniqueResult();
        session.close();
        return user;
    }

    public List<User> getAllUser() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM User ");
        List<User> users = query.list();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    public List<Role> getAllRole() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Role ");
        List<Role> roles = query.list();
        session.getTransaction().commit();
        session.close();
        return roles;
    }

    public User getUserByLogin(String login) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM User where login =:login");
        query.setParameter("login", login);
        User user = null;
        try {
            user = (User) query.uniqueResult();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public void addRole(Role role) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(role);
        session.getTransaction().commit();
        session.close();
    }
}