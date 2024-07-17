package com.francisco.brain.dto;

import com.francisco.brain.entity.ActionEntity;
import com.francisco.brain.entity.DayEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class DayDTO {

  private Long id;
  private LocalDate date;
  private List<ActionDTO> actions;

  public DayDTO() {
  }

  public DayDTO(DayEntity dayEntity) {
    this.id = dayEntity.getId();
    this.date = dayEntity.getDate();
    this.actions = dayEntity.getActions().stream().map(ActionDTO::new).collect(Collectors.toList());
  }

  public DayEntity toEntity() {
    DayEntity dayEntity = new DayEntity();
    dayEntity.setId(this.id);
    dayEntity.setDate(this.date);
    dayEntity.setActions(this.actions.stream().map(ActionDTO::toEntity).collect(Collectors.toList()));
    return dayEntity;
  }
}
