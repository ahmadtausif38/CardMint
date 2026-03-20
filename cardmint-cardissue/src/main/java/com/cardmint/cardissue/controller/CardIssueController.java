package com.cardmint.cardissue.controller;

import com.cardmint.cardissue.model.dto.IssueCardRequestDTO;
import com.cardmint.cardissue.utility.RequestValidationUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cards")
public class CardIssueController {

    private final RequestValidationUtility validationUtility;

    @PostMapping("/issue")
    public ResponseEntity<?> issueCard(
            @RequestBody IssueCardRequestDTO request) {

        // Validate request (outer + nested fields)
        validationUtility.validateIssueCardRequest(request);

        log.info("Validation Passed....");
        return ResponseEntity.ok("response");
    }
}