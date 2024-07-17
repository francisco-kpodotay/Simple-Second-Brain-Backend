package com.francisco.brain.service;

import com.francisco.brain.dto.NewUserDTO;
import com.francisco.brain.dto.UserDTO;
import com.francisco.brain.entity.UserEntity;
import com.francisco.brain.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.francisco.brain.dto.DayDTO;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserDTO createUser(NewUserDTO newUserDTO) {
    if (userRepository.findByUserName(newUserDTO.getUserName()).isPresent()) {
      throw new IllegalArgumentException("User with username " + newUserDTO.getUserName() + " already exists.");
    }

    // Create new UserEntity and set fields
    UserEntity user = new UserEntity();
    user.setUserName(newUserDTO.getUserName());
    user.setPassword(newUserDTO.getPassword());

    // Set default values for other fields
    user.setCountry("Iceland"); // Default value
    user.setLatitude(65.0);  // Default value
    user.setLongitude(-18.0); // Default value
    user.setWorkStartTime(LocalTime.of(9, 0)); // Default value (09:00)
    user.setWorkEndTime(LocalTime.of(17, 0));  // Default value (17:00)

    // Save user and return DTO
    UserEntity savedUser = userRepository.save(user);
    return new UserDTO(savedUser);
  }

  public Optional<UserDTO> getUserByPublicId(String publicId) {
    Optional<UserEntity> userEntity = userRepository.findByPublicId(publicId);
    return userEntity.map(UserDTO::new);
  }

  public ResponseEntity<UserDTO> login(NewUserDTO newUserDTO) {
    Optional<UserEntity> userOptional = userRepository.findByUserName(newUserDTO.getUserName());
    if (userOptional.isPresent()) {
      UserEntity user = userOptional.get();

      if (user.getPassword().equals(newUserDTO.getPassword())) {
        UserDTO userDTO = new UserDTO(user);
        return ResponseEntity.ok(userDTO); // Return 200 OK with UserDTO
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  // Return 401 Unauthorized
      }
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  // Return 401 Unauthorized
    }
  }

  public UserDTO updateUser(String publicId, UserDTO userDetails) {
    System.out.println(userDetails.getCountry());
    return userRepository.findByPublicId(publicId).map(user -> {
      user.setUserName(userDetails.getUserName());
      user.setPassword(userDetails.getPassword());
      user.setCountry(userDetails.getCountry());
      user.setLatitude(userDetails.getLatitude());
      user.setLongitude(userDetails.getLongitude());
      user.setWorkStartTime(userDetails.getWorkStartTime());
      user.setWorkEndTime(userDetails.getWorkEndTime());

      UserEntity savedUser = userRepository.save(user);
      return new UserDTO(savedUser);
    }).orElse(null);
  }


  public boolean deleteUser(Long id) {
    return userRepository.findById(id).map(user -> {
      userRepository.delete(user);
      return true;
    }).orElse(false);
  }

}
