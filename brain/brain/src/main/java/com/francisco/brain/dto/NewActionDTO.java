package com.francisco.brain.dto;

import com.francisco.brain.entity.ActionEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewActionDTO {
  private String name;

  public ActionEntity toEntity() {
    ActionEntity actionEntity = new ActionEntity();
    actionEntity.setName(this.name);
    actionEntity.setComplete(false);
    return actionEntity;
  }
}
