package com.elcho.springboot.service;

import com.elcho.springboot.entity.User;

import java.io.Serializable;
import java.util.List;

public interface IUserService {

    void save(User user);

    void update(User user, Integer id);

    void delete(Integer id);

    User findById(Integer id);

    List<User> findAll();

    User findByUserName(String userName);
}
