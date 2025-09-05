package com.gustalencar.horus.controller.impl;

import com.gustalencar.horus.controller.AuthController;
import com.gustalencar.horus.entity.User;
import com.gustalencar.horus.infra.security.TokenService;
import com.gustalencar.horus.service.UserService;
import lombok.RequiredArgsConstructor;
import models.requests.AuthHorusRequest;
import models.responses.LoginHorusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public ResponseEntity<?> auth(AuthHorusRequest request) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(request.cpf(), request.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok(new LoginHorusResponse(token));
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("CPF ou senha inválidos.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }
}
