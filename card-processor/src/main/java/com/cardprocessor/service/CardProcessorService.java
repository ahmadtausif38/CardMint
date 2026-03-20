package com.cardprocessor.service;

import com.cardprocessor.handler.CardProcessorException;
import com.cardprocessor.handler.ErrorType;
import com.cardprocessor.model.Status;
import com.cardprocessor.model.dto.request.CardIssueRequest;
import com.cardprocessor.model.dto.response.AccountResponse;
import com.cardprocessor.model.dto.response.CardIssueResponse;
import com.cardprocessor.model.entity.AccountEntity;
import com.cardprocessor.model.entity.CardEntity;
import com.cardprocessor.repository.AccountRepository;
import com.cardprocessor.repository.ProcessorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardProcessorService {

    private final ProcessorRepository processorRepository;
    private final AccountRepository accountRepository;

    public CardIssueResponse issueCard(CardIssueRequest request) {
        log.info("CardIssue process started....");
        try {
            log.info("Checking card details by userId :: {}",request.getUserId());
            Optional<CardEntity> cardByUser = processorRepository.findByUserId(request.getUserId());
            if(cardByUser.isPresent() && cardByUser.get().getStatus().equals(Status.ACTIVE.toString())){
                throw new CardProcessorException(ErrorType.CARD_ALREADY_ACTIVATED,
                        "Active card already linked to this user");
            }
            log.info("fetching card details by barcode :: {}",request.getBarcode());
            CardEntity card = processorRepository.findByBarcode(request.getBarcode())
                    .orElseThrow(() -> new CardProcessorException(ErrorType.CARD_NOT_FOUND,
                            "Card not found with given barcode"));

            if (!card.getCardId().equals(request.getCardId())) {
                throw new CardProcessorException(ErrorType.CARD_ID_MISMATCH,
                        "Card not matched with barcode");
            }
            // Mock account creation
            AccountEntity existingAccount = accountRepository.findByUserId(request.getUserId()).orElse(null);
            AccountEntity accountEntity = null;
            AccountResponse accountResponse = null;
            if(existingAccount==null){
               AccountEntity account= AccountEntity.builder()
                       .accountId("acc-"+System.currentTimeMillis())
                       .userId(request.getUserId())
                       .balance(BigDecimal.ZERO)
                       .status(Status.ACTIVE.toString())
                       .currency("INR")
                       .createdAt(Instant.now())
                       .updatedAt(Instant.now())
                       .build();
               accountEntity = accountRepository.save(account);
               log.info("Account is created with accountId {}",accountEntity.getAccountId());
           }
            accountResponse = AccountResponse.builder()
                    .accountId(existingAccount!=null? existingAccount.getAccountId() : accountEntity.getAccountId())
                    .userId(request.getUserId())
                    .balance(existingAccount!=null? existingAccount.getBalance(): accountEntity.getBalance())
                    .status(existingAccount!=null ? existingAccount.getStatus() : accountEntity.getStatus())
                    .currency(existingAccount!=null ? existingAccount.getCurrency() : accountEntity.getCurrency())
                    .createdAt(existingAccount!=null? existingAccount.getCreatedAt() : accountEntity.getCreatedAt())
                    .build();

            card.setUserId(request.getUserId());
            card.setAccountId(existingAccount!=null? existingAccount.getAccountId() : accountEntity.getAccountId());
            card.setActivationDate(Instant.now());
            card.setStatus(Status.ACTIVE.toString());

            processorRepository.save(card);
            log.info("Card is successfully issued to user");
            return CardIssueResponse.builder()
                    .cardId(card.getCardId())
                    .barcode(card.getBarcode())
                    .userId(card.getUserId())
                    .lastFour(card.getLastFour())
                    .status(card.getStatus())
                    .createdAt(card.getCreatedAt())
                    .activationDate(card.getActivationDate())
                    .account(accountResponse)
                    .build();
        } catch (CardProcessorException e) {
            log.error("Exception occurred :: {}",e.getMessage());
            throw new CardProcessorException(ErrorType.CARD_PROCESSOR_ERROR,e.getMessage());
        }
    }
}
