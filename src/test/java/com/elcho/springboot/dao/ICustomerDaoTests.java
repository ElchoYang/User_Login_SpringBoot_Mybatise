package com.elcho.springboot.dao;

import com.elcho.springboot.entity.CustomerLevel;
import com.elcho.springboot.entity.Gender;
import com.elcho.springboot.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ICustomerDaoTests {

   /* @Autowired
    private ICustomerDao customerDao;

    @Test
    public void testSave() {
        Customer c = new Customer();
        c.setName("张三丰");
        c.setPhone("18877665543");
        c.setAge(100);
        c.setGender(Gender.男);
        c.setLevel(CustomerLevel.LOW);
        //
        customerDao.save(c);
    }

    @Test
    public void testFindAll() {
        List<Customer> customers = customerDao.findAll();
        //
        if(customers != null) {
            customers.forEach(System.out::println);
        }
        ///
    }*/
}
