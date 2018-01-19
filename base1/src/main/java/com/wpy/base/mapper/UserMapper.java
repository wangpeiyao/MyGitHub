package com.wpy.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

/**
 * 使用了SpringBoot之后，我们一般不再将sql写在xml映射文件，而是通过相关注解直接写在方法上
 * 所有支持的注解都位于org.apache.ibatis.annotations包中
 */
public interface UserMapper{
    @Insert("INSERT INTO user (id,name) VALUES(#{id},#{name})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    public int insert(User user);
 
    @Update("UPDATE user SET name=#{name} WHERE id=#{id}")
    public int update(User user);
 
    @Select("SELECT * FROM user")
    @Results({
               @Result(id = true, column = "id", property = "id"),
               @Result(column = "name", property = "name"),
            })
    public List<User> selectAll();
 
    @Delete("DELETE FROM user WHERE id=#{id}")
    public int delete(int id);
}