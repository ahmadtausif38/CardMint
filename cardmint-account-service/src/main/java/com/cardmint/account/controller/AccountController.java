package com.cardmint.account.controller;


import com.cardmint.account.model.dto.AccountRequestDTO;
import com.cardmint.account.model.dto.AccountResponseDTO;
import com.cardmint.account.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/test")
    public String test(){
        return "Account service Working....";
    }

    @PostMapping("/create-account")
    public ResponseEntity<AccountResponseDTO> create(@RequestBody AccountRequestDTO requestDTO){
        log.info("Account creation request received with payload : {}",requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(requestDTO));
    }
}
