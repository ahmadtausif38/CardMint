package com.cardmint.cardissue.utility;

import com.cardmint.cardissue.model.dto.AddressDTO;
import com.cardmint.cardissue.model.dto.IssueCardRequestDTO;
import com.cardmint.common.exception.AppException;
import com.cardmint.common.exception.ErrorType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class RequestValidationUtility {

    public void validateIssueCardRequest(IssueCardRequestDTO request) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Map<String, String> errors = new LinkedHashMap<>();

        // --- Outer DTO conditional validation ---
        if (request.isReissue()) {
            if (!StringUtils.hasText(request.userId())) {
                errors.put("userId", "userId is required for re-issue");
            }
        } else { // Fresh issuance

            Set<ConstraintViolation<IssueCardRequestDTO>> reqViolations = validator.validate(request);
            reqViolations.forEach(v -> {
                String field = v.getPropertyPath().toString();
                errors.put(field, v.getMessage());
            });

            if (!StringUtils.hasText(request.fullName())) {
                errors.put("fullName", "fullName is required");
            }
            if (!StringUtils.hasText(request.email())) {
                errors.put("email", "email is required");
            }
            if (!StringUtils.hasText(request.phone())) {
                errors.put("phone", "phone is required");
            }
            if (!StringUtils.hasText(request.pan())) {
                errors.put("pan", "pan is required");
            }
            if (request.dateOfBirth()==null) {
                errors.put("dateOfBirth", "Date of birth is required");
            }
            if (request.address() == null) {
                errors.put("address", "address is required");
            }
        }

        AddressDTO address = request.address();
        if (address != null) {

            Set<ConstraintViolation<AddressDTO>> addViolations = validator.validate(address);
            addViolations.forEach(v -> {
                String field = "address." + v.getPropertyPath().toString();
                errors.put(field, v.getMessage());
            });
        }

        // --- Throw exception if any errors collected ---
        if (!errors.isEmpty()) {
            log.error("Validation Error :: {}", errors);
            throw  new AppException("CRDMNTISS01", "Validation Error", ErrorType.VALIDATION_ERROR.toString(), null, errors);

        }
    }
}
