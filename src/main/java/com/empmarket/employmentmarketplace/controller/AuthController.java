package com.empmarket.employmentmarketplace.controller;

import com.empmarket.employmentmarketplace.dto.res.LoginResponseDto;
import com.empmarket.employmentmarketplace.dto.req.LoginDto;
import com.empmarket.employmentmarketplace.entity.User;
import com.empmarket.employmentmarketplace.service.user.UserService;
import com.empmarket.employmentmarketplace.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {


    private final SecurityUtil securityUtil;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    @Value("${jwt.refresh-token-validity-time}")
    private Long refreshTokenExpiredTime;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(authentication);

        LoginResponseDto res = new LoginResponseDto();
        User currentUser = userService.getUserByEmail(loginDto.getEmail());

        if (currentUser != null) {
            LoginResponseDto.UserLogin userLogin = new LoginResponseDto.UserLogin(
                    currentUser.getId(), currentUser.getEmail(), currentUser.getName());
            res.setUser(userLogin);
        }

        String access_token = securityUtil.generateAccessToken(authentication.getName(), res.getUser());
        res.setAccessToken(access_token);

        String refreshToken = securityUtil.generateRefreshToken(loginDto.getEmail(), res.getUser());

        userService.updateUserToken(refreshToken, loginDto.getEmail());

        ResponseCookie responseCookie = ResponseCookie
                .from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiredTime)
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(res);
    }

    @GetMapping("/account")
    public ResponseEntity<?> getAccount() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ?
                SecurityUtil.getCurrentUserLogin().get() : null;
        User currentUser = userService.getUserByEmail(email);
        LoginResponseDto.UserLogin userLogin = new LoginResponseDto.UserLogin();

        if (currentUser != null) {
            userLogin.setId(currentUser.getId());
            userLogin.setEmail(currentUser.getEmail());
            userLogin.setName(currentUser.getName());
        }

        return ResponseEntity.ok().body(userLogin);
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> getRefreshToken(@CookieValue(name = "refreshToken") String refreshToken) {

        Jwt token = securityUtil.checkValidRefreshToken(refreshToken);
        String email = token.getSubject();

        User currentUser = userService.getUserByRefreshTokenAndEmail(refreshToken, email);
        if (currentUser == null) {
            System.out.println("Invalid Refresh Token");
        }

        LoginResponseDto res = new LoginResponseDto();
        User currentUserDB = userService.getUserByEmail(email);

        if (currentUserDB != null) {
            LoginResponseDto.UserLogin userLogin = new LoginResponseDto.UserLogin(
                    currentUserDB.getId(), currentUserDB.getEmail(), currentUserDB.getName());
            res.setUser(userLogin);
        }

        String access_token = securityUtil.generateAccessToken(email, res.getUser());
        res.setAccessToken(access_token);

        String newRefreshToken = securityUtil.generateRefreshToken(email, res.getUser());

        userService.updateUserToken(refreshToken, email);

        ResponseCookie responseCookie = ResponseCookie
                .from("refreshToken", newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiredTime)
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(res);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        String email = SecurityUtil.getCurrentUserLogin().orElse(null);

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
        }

        userService.updateUserToken(null, email);

        ResponseCookie deleteCookie = ResponseCookie
                .from("refreshToken", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, deleteCookie.toString()).body("Successfully logged out");
    }

}
