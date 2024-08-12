package com.francisco.brain.service;

import com.francisco.brain.dto.ActionDTO;
import com.francisco.brain.dto.DayDTO;
import com.francisco.brain.dto.NewActionDTO;
import com.francisco.brain.entity.ActionEntity;
import com.francisco.brain.entity.DayEntity;
import com.francisco.brain.entity.UserEntity;
import com.francisco.brain.repository.ActionRepository;
import com.francisco.brain.repository.DayRepository;
import com.francisco.brain.repository.UserRepository;
import jakarta.transaction.Transactional;
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

  public List<DayDTO> getDaysInRange(String publicId, LocalDate startDate, LocalDate endDate) {
    UserEntity userEntity = userRepository.findByPublicId(publicId).orElseThrow(() -> new RuntimeException("User not found"));
    List<DayDTO> days = new ArrayList<>();
    LocalDate currentDate = startDate;

    while (!currentDate.isAfter(endDate)) {
      Optional<DayEntity> dayEntity = dayRepository.findByUserIdAndDate(userEntity.getId(), currentDate);
      days.add(dayEntity.map(DayDTO::new).orElse(null));
      currentDate = currentDate.plusDays(1);
    }

    return days;
  }

  public ActionDTO addAction(String publicId, LocalDate date, NewActionDTO actionDTO) {
    UserEntity userEntity = userRepository.findByPublicId(publicId).orElseThrow(() -> new RuntimeException("User not found"));
    DayEntity dayEntity = dayRepository.findByDateAndUserId(date, userEntity.getId()).orElseGet(() -> {
      DayEntity newDay = new DayEntity();
      newDay.setUser(userEntity);
      newDay.setDate(date);
      return dayRepository.save(newDay);
    });

    ActionEntity actionEntity = actionDTO.toEntity();
    actionEntity.setDay(dayEntity);
    ActionEntity savedAction = actionRepository.save(actionEntity);

    return new ActionDTO(savedAction);
  }

  public Optional<ActionDTO> updateAction(String publicId, Long dayId, Long actionId, ActionDTO actionDTO) {
    ActionEntity actionEntity = getActionEntity(publicId, dayId, actionId);

    actionEntity.setName(actionDTO.getName());
    actionEntity.setComplete(actionDTO.isComplete());

    ActionEntity updatedAction = actionRepository.save(actionEntity);
    return Optional.of(new ActionDTO(updatedAction));
  }

  @Transactional
  public boolean deleteAction(String publicId, Long dayId, Long actionId) {
    ActionEntity actionEntity = getActionEntity(publicId, dayId, actionId);

    //TO-DO
    actionRepository.myDeleteById(actionEntity.getId());
    return true;
  }

  private ActionEntity getActionEntity(String publicId, Long dayId, Long actionId) {
    UserEntity userEntity = userRepository.findByPublicId(publicId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    DayEntity dayEntity = userEntity.getDays().stream()
            .filter(day -> day.getId().equals(dayId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Day not found for the user"));

    return dayEntity.getActions().stream()
            .filter(action -> action.getId().equals(actionId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Action not found for the day"));
  }
}
