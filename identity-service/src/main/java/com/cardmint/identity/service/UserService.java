package com.cardmint.identity.service;

import com.cardmint.common.exception.AppException;
import com.cardmint.common.exception.ErrorType;
import com.cardmint.identity.dto.request.CreateUserRequest;
import com.cardmint.identity.dto.response.ApiResponse;
import com.cardmint.identity.dto.response.UserOnBoardingResponse;
import com.cardmint.identity.entity.UserEntity;
import com.cardmint.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final static Logger logger= LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final IdentityKafkaProducerService identityKafkaProducerService;
    private final SecurityService securityService;

    public ApiResponse registerUser(CreateUserRequest userRequest){

        try {
            if(userRepository.findByPanHash(userRequest.pan()).isPresent()){
                throw new AppException("IDDTY01","PAN Already linked to user", String.valueOf(ErrorType.BUSINESS_ERROR), HttpStatus.BAD_REQUEST);
            }

            UserEntity.Address userAddress = new UserEntity.Address(
                    userRequest.address().line1(),
                    userRequest.address().line2(),
                    userRequest.address().city(),
                    userRequest.address().postalCode(),
                    userRequest.address().state(),
                    userRequest.address().country()
            );

            UserEntity userEntity=UserEntity.builder()
                    .phone(userRequest.phone())
                    .email(userRequest.email())
                    .fullName(userRequest.fullName())
                    .panHash(securityService.hashPan(userRequest.pan()))
                    .panEncrypted(securityService.encryptPan(userRequest.pan()))
                    .dateOfBirth(userRequest.dateOfBirth())
                    .address(userAddress)
                    .build();
            UserEntity userResponse = userRepository.save(userEntity);
            identityKafkaProducerService.publishKafkaEvent(userResponse);
            return sendResponse(userResponse);

        } catch (Exception e) {
            logger.error("Exception occurred :: {}",e.getMessage(),e);
            if(e instanceof AppException ex){
                throw ex;
            }
            throw new RuntimeException(e);
        }
    }

    private ApiResponse sendResponse(UserEntity userResponse) {

        return ApiResponse.<UserOnBoardingResponse>builder()
                .success(true)
                .message("Customer onboarded successfully. Ready for card issuance.")
                .data(UserOnBoardingResponse.builder()
                        .userId(userResponse.getUserId())
                        .fullName(userResponse.getFullName())
                        .email(userResponse.getEmail())
                        .build())
                .build();
    }

}
