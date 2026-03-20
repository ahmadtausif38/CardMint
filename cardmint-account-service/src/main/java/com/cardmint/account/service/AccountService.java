package com.cardmint.account.service;

import com.cardmint.account.constants.StatusEnum;
import com.cardmint.account.model.dto.AccountRequestDTO;
import com.cardmint.account.model.dto.AccountResponseDTO;
import com.cardmint.account.model.entity.Account;
import com.cardmint.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final AtomicInteger sequence = new AtomicInteger(0);
    private String lastSecond = "";


    public AccountResponseDTO createAccount(AccountRequestDTO requestDTO) {
        String accountId = generateAccountId(); // private method
        Account account = Account.builder()
                .accountId(accountId)
                .currency("INR")
                .balance(BigDecimal.ZERO)
                .status(String.valueOf(StatusEnum.ACTIVE))
                .build();

        Account savedAccount = accountRepository.save(account);
        
        return getAccountResponse(savedAccount);
    }

    private AccountResponseDTO getAccountResponse(Account savedAccount) {
        return new AccountResponseDTO(
                savedAccount.getAccountId(),
                savedAccount.getCurrency(),
                savedAccount.getBalance(),
                savedAccount.getStatus(),
                savedAccount.getCreatedAt(),
                savedAccount.getUpdatedAt()
        );
    }

    // Private helper for generating accountId
    private synchronized String generateAccountId() {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        if (!now.equals(lastSecond)) {
            sequence.set(1);
            lastSecond = now;
        }

        int seq = sequence.getAndIncrement();
        return "acc-" + now + String.format("%03d", seq);
    }
}
