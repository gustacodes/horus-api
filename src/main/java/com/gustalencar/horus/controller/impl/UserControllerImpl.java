package com.gustalencar.horus.controller.impl;

import com.gustalencar.horus.controller.UserController;
import com.gustalencar.horus.service.UserService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateUserHorusRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService service;

    @Override
    public ResponseEntity<Void> save(final CreateUserHorusRequest request) {
        service.save(request);
        return ResponseEntity.status(CREATED.value()).build();
    }

}
