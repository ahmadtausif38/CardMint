package com.cardprocessor.controller;

import com.cardprocessor.model.dto.request.CardIssueRequest;
import com.cardprocessor.model.dto.response.CardIssueResponse;
import com.cardprocessor.service.CardProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class ProcessorController {

    private final CardProcessorService cardProcessorService;

    @PostMapping("/issue")
    public ResponseEntity<CardIssueResponse> issueCard(@RequestBody CardIssueRequest request) {
        CardIssueResponse response = cardProcessorService.issueCard(request);
        return ResponseEntity.ok(response);
    }
}
