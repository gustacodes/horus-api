package com.gustalencar.horus.controller.impl;

import com.gustalencar.horus.controller.UserController;
import com.gustalencar.horus.service.UserService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateUserHorusRequest;
import models.responses.UserHorusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService service;

    @Override
    public ResponseEntity<Void> save(CreateUserHorusRequest request, byte[] fingerprintTemplate) {
        service.save(request, fingerprintTemplate);
        return ResponseEntity.status(CREATED.value()).build();
    }

    @Override
    public ResponseEntity<UserHorusResponse> findById(final Long id) {
        return ResponseEntity.status(OK).body(service.findById(id));
    }

}
