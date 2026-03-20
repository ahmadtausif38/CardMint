package com.cardmint.identity.service;

import com.cardmint.common.exception.AppException;
import com.cardmint.common.exception.ErrorType;
import com.cardmint.identity.dto.CreateUserRequest;
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

    public UserEntity registerUser(CreateUserRequest userRequest){

        try {
            if(userRepository.findByPanHash(userRequest.pan()).isPresent()){
                throw new AppException("IDDTY01","PAN Already linked to user", String.valueOf(ErrorType.BUSINESS_ERROR), HttpStatus.BAD_REQUEST);
            }
            UserEntity userEntity=UserEntity.builder()
                    .phone(userRequest.phone())
                    .email(userRequest.email())
                    .fullName(userRequest.fullName())
                    .panHash(userRequest.pan())
                    .build();
             var saveduser = userRepository.save(userEntity);
            identityKafkaProducerService.publishKafkaEvent(saveduser);
            return saveduser;

        } catch (Exception e) {
            logger.error("Exception occurred :: {}",e.getMessage(),e);
            if(e instanceof AppException ex){
                throw ex;
            }
            throw new RuntimeException(e);
        }
    }

}
