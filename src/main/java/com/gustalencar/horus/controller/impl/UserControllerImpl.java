package com.gustalencar.horus.controller.impl;

import com.gustalencar.horus.controller.UserController;
import com.gustalencar.horus.infra.email.EmailService;
import com.gustalencar.horus.service.UserService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateUserHorusRequest;
import models.responses.UserHorusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService service;
    private final EmailService emailService;

    @Override
    public ResponseEntity<Void> save(CreateUserHorusRequest request, byte[] fingerprintTemplate) {
        service.save(request, fingerprintTemplate);
        return ResponseEntity.status(CREATED.value()).build();
    }

    @Override
    public ResponseEntity<UserHorusResponse> findById(final Long id) {
        return ResponseEntity.status(OK).body(service.findById(id));
    }

    @Override
    public ResponseEntity<Void> sendEmail(String email, String message, String subject) {
        emailService.enviarEmail(email, subject, message);
        return ResponseEntity.ok().build();
    }

}
