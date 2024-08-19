package com.empmarket.employmentmarketplace.controller;

import com.empmarket.employmentmarketplace.dto.req.SignupDto;
import com.empmarket.employmentmarketplace.dto.req.UserDto;
import com.empmarket.employmentmarketplace.service.user.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> createUser(@RequestBody SignupDto signupDto) {
        try {
            UserDto createdUser = userService.createUser(signupDto);
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot create user", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot get user by id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{pageNumber}")
    public ResponseEntity<?> getAllUsers(@PathVariable int pageNumber) {
        return ResponseEntity.ok(userService.getAllUsers(pageNumber));
    }

    @GetMapping("/users/search")
    public ResponseEntity<?> searchUsers(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) String email,
                                         @RequestParam(required = false, defaultValue = "AND") String predicateType,
                                         @RequestParam(defaultValue = "0") int pageNumber,
                                         @RequestParam(defaultValue = "6") int pageSize,
                                         @RequestParam(required = false) String sortBy,
                                         @RequestParam(required = false) String sortDir) {
        return ResponseEntity.ok(userService.searchUsers(name, email, predicateType, pageNumber, pageSize, sortBy, sortDir));
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        boolean success = userService.updateUser(userId, userDto);
        if(success) {
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
