package com.francisco.brain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewUserDTO {

  private String userName;
  private String password;

  public NewUserDTO() {
  }

  public NewUserDTO(String name, String password) {
    this.userName = name;
    this.password = password;
  }
}
