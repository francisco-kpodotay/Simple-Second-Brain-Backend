package com.francisco.brain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class ActionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private boolean complete;

  @ManyToOne()
  //@JoinColumn(name = "day_id")
  private DayEntity day;
}
