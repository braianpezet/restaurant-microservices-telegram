package com.auth.controller;

import com.auth.JwtUtils;
import com.auth.controller.dto.UserDto;
import com.auth.controller.dto.UserInformation;
import com.auth.entity.AuthToken;
import com.auth.entity.AuthUser;
import com.auth.entity.JwtUserDetails;
import com.auth.controller.dto.LoginDto;
import com.auth.error.UserNotFoundRuntimeException;
import com.auth.service.AuthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/oauth/users")
public class UserController {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthUser register(@RequestBody UserDto userDto) {
        return authUserService.register(userDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthToken login(@RequestBody LoginDto loginDto) throws UserNotFoundRuntimeException {
        return authUserService.getLoginToken(loginDto);
    }

    @GetMapping("/validate")
    public JwtUserDetails validate(@RequestHeader("x-auth-token") String authToken) {
        return jwtUtils.validateToken(authToken);
    }
    @GetMapping("/information")
    public UserInformation validateWithInformation(@RequestHeader("x-auth-token") String authToken){
        return jwtUtils.validateTokenAndInformation(authToken);
    }

/*
    @PostMapping("/login-test")
    @ResponseStatus(HttpStatus.OK)
    public AuthToken login2(@RequestBody HashMap map) {
        log.info(String.valueOf(map));
        LoginDto loginDto = new LoginDto();
        loginDto.setUserName(map.get("user_name").toString());
        loginDto.setPassword(map.get("password").toString());
        loginDto.setGrantType("password");
        return authUserService.getLoginToken(loginDto);
    }*/
}
