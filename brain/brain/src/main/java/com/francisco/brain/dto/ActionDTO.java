package com.francisco.brain.dto;

import com.francisco.brain.entity.ActionEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActionDTO {

  private Long id;
  private String name;
  private boolean complete;

  public ActionDTO() {
  }

  public ActionDTO(ActionEntity actionEntity) {
    this.id = actionEntity.getId();
    this.name = actionEntity.getName();
    this.complete = actionEntity.isComplete();
  }

  public ActionEntity toEntity() {
    ActionEntity actionEntity = new ActionEntity();
    actionEntity.setId(this.id);
    actionEntity.setName(this.name);
    actionEntity.setComplete(this.complete);
    return actionEntity;
  }
}
