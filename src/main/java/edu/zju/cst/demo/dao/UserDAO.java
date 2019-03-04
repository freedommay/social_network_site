package edu.zju.cst.demo.dao;

import edu.zju.cst.demo.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDAO {
    String TABLE_NAME = " user ";
    String INSERT_FIELDS = " name, password, salt, head_url, last_login_time ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into",  TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{name}, #{password}, #{salt}, #{headURL}, #{lastLoginTime})"})
    int addUser(User user);

    @Select({"select", SELECT_FIELDS, " from ", TABLE_NAME, "where id=#{id}"})
    User selectById(int id);

    @Select({"select", SELECT_FIELDS, " from ", TABLE_NAME, "where name=#{name}"})
    User selectByName(String name);

    @Update({"update", TABLE_NAME, "set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Update({"update", TABLE_NAME, "set last_login_time=#{lastLoginTime} where id=#{id}" })
    void updateLastUpdateTime(User user);

    @Delete({"delete from", TABLE_NAME, "where id=#{id}"})
    void deleteById(int id);
}
