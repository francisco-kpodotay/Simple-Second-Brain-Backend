package com.francisco.brain.repository;

import com.francisco.brain.entity.ActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActionRepository extends JpaRepository<ActionEntity, Long> {
  Optional<ActionEntity> findByIdAndDayId(Long actionId, Long dayId);

  @Modifying
  @Query("DELETE from ActionEntity a where a.id = ?1")
  void myDeleteById(Long actionId);
}
