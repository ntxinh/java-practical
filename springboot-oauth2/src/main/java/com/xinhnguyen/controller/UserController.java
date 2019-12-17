package com.xinhnguyen.controller;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xinhnguyen.domain.User;
import com.xinhnguyen.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepo;
	
	@RequestMapping({ "/user", "/me" })
	public Map<String, String> user(Principal principal) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("name", principal.getName());
		
		
		// Save user
		User user = new User();
		user.setName(principal.getName());
		userRepo.save(user);
		
		return map;
	}
}
