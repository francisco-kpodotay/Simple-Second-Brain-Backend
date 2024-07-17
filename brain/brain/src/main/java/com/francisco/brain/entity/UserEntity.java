package com.francisco.brain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, updatable = false)
  private String publicId;

  @Column(nullable = false)
  private String userName;

  @Column(nullable = false)
  private String password;
  private String country;
  private Double latitude;
  private Double longitude;
  private LocalTime workStartTime;
  private LocalTime workEndTime;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<DayEntity> days;

  // Generate and set the public ID before persisting the entity
  @PrePersist
  public void generatePublicId() {
    this.publicId = UUID.randomUUID().toString();
  }
}
