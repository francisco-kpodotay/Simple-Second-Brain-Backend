package com.francisco.brain.controller;

import com.francisco.brain.dto.ActionDTO;
import com.francisco.brain.dto.DayDTO;
import com.francisco.brain.dto.NewUserDTO;
import com.francisco.brain.dto.UserDTO;
import com.francisco.brain.service.DayService;
import com.francisco.brain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<UserDTO> createUser(@RequestBody NewUserDTO newUserDTO) {
    UserDTO createdUser = userService.createUser(newUserDTO);
    return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
  }

  @GetMapping("/{publicId}")
  public ResponseEntity<Optional<UserDTO>> getUserByPublicId(@PathVariable String publicId) {
    Optional<UserDTO> user = userService.getUserByPublicId(publicId);
    if (user.isPresent()) {
      return new ResponseEntity<>(user, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<UserDTO> login(@RequestBody NewUserDTO newUserDTO) {
    return userService.login(newUserDTO);
  }

  @PutMapping("/{publicId}")
  public ResponseEntity<Optional<UserDTO>> updateUser(@PathVariable String publicId, @RequestBody UserDTO userDetails) {
    Optional<UserDTO> updatedUser = Optional.ofNullable(userService.updateUser(publicId, userDetails));
    if (updatedUser.isPresent()) {
      return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    boolean isDeleted = userService.deleteUser(id);
    if (isDeleted) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
