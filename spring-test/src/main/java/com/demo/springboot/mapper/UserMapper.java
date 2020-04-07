package com.demo.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.demo.springboot.entity.User;

@Mapper
public interface UserMapper {
		
	@Select("select * from tb_user where id=${id}")
	@Results(id="UserMap",value= {
			@Result(column="id",property="id"),
			@Result(column="username",property="username"),
			@Result(column="address",property="address")
	})
	User getUser(User user);
	
	@Select("select * from tb_user")
	@ResultMap(value="UserMap")
	List<User> getUserList();
	
	@Insert("insert into tb_user(username,address) values(#{username},#{address})")
	int addUser(User user);
	
	@Delete("delete from tb_user where id=#{id}")
	int delUser(User user);
	
	@Update("update tb_user set username=#{username},address=#{address} where id=#{id}")
	int updateUser(User user);
}
