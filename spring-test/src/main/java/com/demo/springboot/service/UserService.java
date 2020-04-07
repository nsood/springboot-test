package com.demo.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.springboot.entity.User;
import com.demo.springboot.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public User getUser(User user) {
		return userMapper.getUser(user);
	}
	
	public List<User> getUserList(){
		return userMapper.getUserList();
	}
	
	@Transactional
	public int addUser(User user) {
		return userMapper.addUser(user);
	}
	@Transactional
	public int delUser(User user) {
		return userMapper.delUser(user);
	}
	@Transactional
	public int updateUser(User user) {
		return userMapper.updateUser(user);
	}
}
