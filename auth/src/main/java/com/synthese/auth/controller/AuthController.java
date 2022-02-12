package com.synthese.auth.controller;

import com.synthese.auth.model.Client;
import com.synthese.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.synthese.auth.utils.Utils.CROSS_ORIGIN_ALLOWED;
import static com.synthese.auth.utils.Utils.AuthControllerUrl.*;

@RestController
@CrossOrigin(CROSS_ORIGIN_ALLOWED)
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping(URL_SIGN_UP_CLIENT)
    public ResponseEntity<Client> signUpStudent(@RequestBody Client client) {
        return service.signUp(client)
                .map(_student -> ResponseEntity.status(HttpStatus.CREATED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

}
