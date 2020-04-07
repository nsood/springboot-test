package com.demo.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springboot.entity.User;
import com.demo.springboot.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/user/{id}")
	public User getUser(@PathVariable int id){
		User user = new User();
		user.setId(id);
		return userService.getUser(user);
	}
	
	@GetMapping("/userAll")
	public List<User> getUserList(){
		return userService.getUserList();
	}
	
	@PostMapping("/user")
	public int addUser(
			@RequestParam(value="username", required=true) String username,
			@RequestParam(value="address", required=true) String address) {
		User user = new User();
		user.setUsername(username);
		user.setAddress(address);
		return userService.addUser(user);
	}
	
	@DeleteMapping("/user/{id}")
	public int delUser(@PathVariable int id) {
		User user = new User();
		user.setId(id);
		return userService.delUser(user);
	}
	
	@PutMapping("/user/{id}")
	public int updateUser(
			@PathVariable int id,
			@RequestParam(value="username", required=true) String username,
			@RequestParam(value="address", required=true) String address) {
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setAddress(address);
		return userService.updateUser(user);
	}
}
