package com.elcho.springboot.service;

import com.elcho.springboot.entity.CusUser;

import java.io.Serializable;
import java.util.List;

public interface IUserService {

    void save(CusUser user);

    void update(CusUser user, Integer id);

    void delete(Integer id);

    CusUser findById(Integer id);

    List<CusUser> findAll();

    CusUser findByUserName(String userName);
}
