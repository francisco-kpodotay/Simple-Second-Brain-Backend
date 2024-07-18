package com.francisco.brain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
public class DayEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true, nullable = false)
  private LocalDate date;
  @ManyToOne
  //@JoinColumn(name = "user_id")
  private UserEntity user;

  @OneToMany(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ActionEntity> actions;
}
