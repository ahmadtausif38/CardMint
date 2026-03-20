package com.cardprocessor.repository;

import com.cardprocessor.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity,Integer> {

    Optional<AccountEntity>findByUserId(String userId);
}
