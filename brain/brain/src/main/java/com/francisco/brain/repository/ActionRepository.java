package com.francisco.brain.repository;

import com.francisco.brain.entity.ActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActionRepository extends JpaRepository<ActionEntity, Long> {
  Optional<ActionEntity> findByIdAndDayId(Long actionId, Long dayId);
}
