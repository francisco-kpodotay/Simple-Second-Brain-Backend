package com.francisco.brain.repository;

import com.francisco.brain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByPublicId(String publicId);
  Optional<UserEntity> findByUserName(String userName);
}
