package com.cardmint.identity.controller;

//import com.cardmint.identity.aspect.LogginAnnotation;
import com.cardmint.identity.aspect.LogginAnnotation;
import com.cardmint.identity.dto.request.CreateUserRequest;
import com.cardmint.identity.dto.response.ApiResponse;
import com.cardmint.identity.entity.UserEntity;
import com.cardmint.identity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class IdentityController {

    private static final Logger logger= LoggerFactory.getLogger(IdentityController.class);

    private final UserService userService;

    @GetMapping("/test")
    public String testing(){
        return "Identity Service is working fine..";
    }

    @PostMapping("/register-user")
    @LogginAnnotation
    public ApiResponse register(@RequestBody CreateUserRequest request){
        logger.info("Registering user");
        return userService.registerUser(request);
    }
}
