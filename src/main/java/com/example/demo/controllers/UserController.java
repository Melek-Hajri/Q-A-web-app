package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.RoleType;
import com.example.demo.entities.User;
import com.example.demo.entities.exceptions.ResourceAlreadyExistsException;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.services.UserServImp;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
    private UserServImp userService;


    @PostMapping(value = "/Add")
    public ResponseEntity<User> addUser(@RequestParam String username,
                                        @RequestParam String email,
                                        @RequestParam int roleValue) {
        try {
        	RoleType role = RoleType.fromValue(roleValue);
            User newUser = userService.userAdd(username, email, role);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/Find/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.userFind(userId);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/FindAll")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.userFindAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/Update/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId,
                                           @RequestBody User updatedUser) {
        try {
            User updated = userService.userUpdate(userId, updatedUser);
            return ResponseEntity.ok(updated);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/Delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        try {
            userService.userDelete(userId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
