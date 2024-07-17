package com.francisco.brain.repository;

import com.francisco.brain.entity.DayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DayRepository extends JpaRepository<DayEntity, Long> {
  Optional<DayEntity> findByUserIdAndDate(Long userId, LocalDate date);
  Optional<DayEntity> findByIdAndUserId(Long dayId, Long userId);
  Optional<DayEntity> findByDateAndUserId(LocalDate date, Long userId);

}
