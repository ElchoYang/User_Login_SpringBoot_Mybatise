package com.elcho.springboot.dao;

import com.elcho.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;

@Mapper
public interface IUserDao {
    /*********
     * 插入一个帐户对象到数据库中
     * @param user
     */
    @Insert("insert into tbl_user(name,role,email,username,password) " +
            "values (#{name}, #{role} ,#{email} ,#{userName} ,#{password})")
    void save(User user);

    /***************
     * 根据帐户ID来查询帐户信息
     * @param id
     * @return
     */
    @Select("select id,name,role,email,username,password from tbl_user where id = #{id}")
    User findById(Serializable id);

    /****************
     * 查询所有帐户信息
     * @return
     */
    @Select("select * from tbl_user")
    List<User> findAll();

    /*****************
     * 更新帐户
     * @param user
     * (name,role,email,username,password) " +
     *             "values (#{name},#{role} ,#{email} ,#{userName} ,#{password})
     */
    @Update("update tbl_user set name = #{user.name}, role = #{user.role}, email = #{user.email}, username = #{user.userName}, password=#{user.password} where id = #{id}")
    void update(User user, Integer id);

    /***************
     * 删除帐户
     * @param id
     */
    @Delete("delete from tbl_user where id = #{id}")
    void delete(Serializable id);

    /**********
     * 根据帐户ID来查询它所属的用户信息
     * @param accountId
     * @return
     */
    User queryByAccountId(Serializable accountId);

    /***********
     * 根据用户名来查询用户信息
     * @param userName
     * @return
     */
    @Select("select id,name,role,username,password from tbl_user where username = #{username}")
    User queryByUserName(String userName);
}
