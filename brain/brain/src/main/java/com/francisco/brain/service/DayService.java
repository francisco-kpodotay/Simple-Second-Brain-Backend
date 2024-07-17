package com.francisco.brain.service;

import com.francisco.brain.dto.ActionDTO;
import com.francisco.brain.dto.DayDTO;
import com.francisco.brain.entity.ActionEntity;
import com.francisco.brain.entity.DayEntity;
import com.francisco.brain.repository.ActionRepository;
import com.francisco.brain.repository.DayRepository;
import com.francisco.brain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DayService {

  private final UserRepository userRepository;
  private final DayRepository dayRepository;
  private final ActionRepository actionRepository;

  public DayService(UserRepository userRepository, DayRepository dayRepository, ActionRepository actionRepository) {
    this.userRepository = userRepository;
    this.dayRepository = dayRepository;
    this.actionRepository = actionRepository;
  }

  public List<DayDTO> getDaysInRange(Long userId, LocalDate startDate, LocalDate endDate) {
    List<DayDTO> days = new ArrayList<>();
    LocalDate currentDate = startDate;

    while (!currentDate.isAfter(endDate)) {
      Optional<DayEntity> dayEntity = dayRepository.findByUserIdAndDate(userId, currentDate);
      days.add(dayEntity.map(DayDTO::new).orElse(null));
      currentDate = currentDate.plusDays(1);
    }

    return days;
  }

  public ActionDTO addAction(Long userId, Long dayId, ActionDTO actionDTO) {
    DayEntity dayEntity = dayRepository.findByIdAndUserId(dayId, userId).orElseThrow(() -> new RuntimeException("Day not found"));
    ActionEntity actionEntity = actionDTO.toEntity();
    actionEntity.setDay(dayEntity);
    return new ActionDTO(actionRepository.save(actionEntity));
  }

  public Optional<ActionDTO> updateAction(Long userId, Long dayId, Long actionId, ActionDTO actionDTO) {
    return actionRepository.findByIdAndDayId(actionId, dayId).map(action -> {
      action.setName(actionDTO.getName());
      action.setComplete(actionDTO.isComplete());
      return new ActionDTO(actionRepository.save(action));
    });
  }

  public boolean deleteAction(Long userId, Long dayId, Long actionId) {
    return actionRepository.findByIdAndDayId(actionId, dayId).map(action -> {
      actionRepository.delete(action);
      return true;
    }).orElse(false);
  }
}
