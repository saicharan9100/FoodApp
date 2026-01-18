package com.example.daoimpl;

import org.hibernate.query.Query;

import com.example.dao.UserDao;
import com.example.domain.User;

public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

    @Override
    public void saveUser(User user) {
        save(user);
    }

    @Override
    public void deleteUser(User user) {
        delete(user);
    }

    @Override
    public void updateUser(User user) {
        update(user);
    }

    @Override
    public User getUserById(Long id) {
        return findById(User.class, id);
    }

    @Override
    public java.util.List<User> findAllUsers() {
        return findAll(User.class);
    }

    @Override
    public User findByUsername(String username) {
        Query<User> query = sessionFactory.getCurrentSession().createQuery("from User where username=:username", User.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }

}
