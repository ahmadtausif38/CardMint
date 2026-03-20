package com.cardmint.identity.repository;

import com.cardmint.identity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,String> {

    public Optional<UserEntity> findByPanHash(String pan);
}
