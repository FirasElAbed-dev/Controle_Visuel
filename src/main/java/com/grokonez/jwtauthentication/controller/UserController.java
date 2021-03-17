package com.grokonez.jwtauthentication.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grokonez.jwtauthentication.repository.UserRepository;
import com.attijari.demo.exception.ResourceNotFoundException;
import com.grokonez.jwtauthentication.model.User;

@RestController

@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/users")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<User> getAllUser() {
		return userRepository.findAll();
	}
	
	@GetMapping("/users/id/{userId}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Optional<User> getUserById(@PathVariable(value = "userId") Long userId) {
		return userRepository.findById(userId);
	}

	
	/*
	 * @PostMapping("/users")
	 * 
	 * @PreAuthorize("hasRole('ADMIN')") public User createUser(@RequestBody User
	 * param) { return userRepository.save(param); }
	 */

	@PutMapping("/users/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public User updateUser(@PathVariable Long userId, @RequestBody User userRequest) {
		return userRepository.findById(userId).map(user -> {
			user.setName(userRequest.getName());
			user.setUsername(userRequest.getUsername());
			user.setEmail(userRequest.getEmail());
			user.setPassword(userRequest.getPassword());
			//roles
			user.setRoles(userRequest.getRoles());
			return userRepository.save(user);
		}).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
	}


	@DeleteMapping("/users/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
		return userRepository.findById(userId).map(user -> {
			userRepository.delete(user);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
	}
}
