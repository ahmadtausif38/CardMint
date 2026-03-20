package com.cardprocessor.repository;

import com.cardprocessor.model.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessorRepository extends JpaRepository<CardEntity, Integer> {
    Optional<CardEntity> findByCardId(String cardId);
    Optional<CardEntity> findByBarcode(String barcode);
    Optional<CardEntity> findByUserId(String userId);
}