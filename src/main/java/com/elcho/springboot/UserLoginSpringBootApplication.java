package com.elcho.springboot;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication()
public class UserLoginSpringBootApplication {

    public static void main(String[] args) {

        SpringApplication.run(UserLoginSpringBootApplication.class, args);


        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:elcho");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        JdbcTemplate template = new JdbcTemplate(dataSource);

//        create customer table
      /*  String sql = "drop table  if exists tbl_customer;";
        template.execute(sql);
        sql = "create table tbl_customer(    id integer primary key auto_increment, name varchar (255), phone varchar (32) not null unique, c_level varchar (25), age smallint, gender varchar (8));";
        template.execute(sql);
        sql = "insert into tbl_customer(name,phone,c_level, age,gender) values('admin','133333333','HIGH','55','男')";
        template.update(sql);*/

        // create user table
        String sql = "drop table  if exists TBL_USER;";
        template.execute(sql);
        sql = "create table TBL_USER(ID INTEGER  auto_increment unique, NAME CHAR, ROLE CHAR, email CHAR, USERNAME CHAR,PASSWORD CHAR);";
        template.execute(sql);
        sql = "insert into TBL_USER(name,role,email, USERNAME,PASSWORD) values('admin','ROLE_ADMIN','admin@ibm.com','admin','admin')";
        template.update(sql);

        System.out.println("Create done =======");

    }

}
