package com.elcho.springboot.service.impl;

import com.elcho.springboot.dao.IUserDao;
import com.elcho.springboot.entity.User;
import com.elcho.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl  implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void update(User user, Integer id) {
        userDao.update(user, id);
    }

    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findByUserName(String userName) {
        return userDao.queryByUserName(userName);
    }
}
