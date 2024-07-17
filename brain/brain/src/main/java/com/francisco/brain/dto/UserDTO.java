package com.francisco.brain.dto;

import com.francisco.brain.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class UserDTO {

  private Long id;
  private String publicId;
  private String userName;
  private String password;
  private String country;
  private Double latitude;
  private Double longitude;
  private LocalTime workStartTime;
  private LocalTime workEndTime;
  private List<DayDTO> days;

  public UserDTO() {
  }

  public UserDTO(UserEntity userEntity) {
    this.id = userEntity.getId();
    this.publicId = userEntity.getPublicId();
    this.userName = userEntity.getUserName();
    this.password = userEntity.getPassword();
    this.country = userEntity.getCountry();
    this.latitude = userEntity.getLatitude();
    this.longitude = userEntity.getLongitude();
    this.workStartTime = userEntity.getWorkStartTime();
    this.workEndTime = userEntity.getWorkEndTime();
    this.days = userEntity.getDays() != null ?
            userEntity.getDays().stream().map(DayDTO::new).collect(Collectors.toList()) :
            new ArrayList<>();}

  public UserEntity toEntity() {
    UserEntity userEntity = new UserEntity();
    userEntity.setId(this.id);
    userEntity.setPublicId(this.publicId);
    userEntity.setUserName(this.userName);
    userEntity.setPassword(this.password);
    userEntity.setCountry(this.country);
    userEntity.setLatitude(this.latitude);
    userEntity.setLongitude(this.longitude);
    userEntity.setWorkStartTime(this.workStartTime);
    userEntity.setWorkEndTime(this.workEndTime);
    userEntity.setDays(this.days != null ?
            this.days.stream().map(DayDTO::toEntity).collect(Collectors.toList()) :
            new ArrayList<>());
    return userEntity;
  }
}
