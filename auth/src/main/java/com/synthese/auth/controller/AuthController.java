package com.synthese.auth.controller;

import com.synthese.auth.model.Admin;
import com.synthese.auth.model.Client;
import com.synthese.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Client> signUpClient(@RequestBody Client client) {
        return service.signUpClient(client)
                .map(_client -> ResponseEntity.status(HttpStatus.CREATED).body(_client))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_LOGIN_CLIENT)
    public ResponseEntity<Client> loginClient(@PathVariable String email, @PathVariable String password) {
        return service.loginClient(email, password)
                .map(_client -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_client))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_LOGIN_ADMIN)
    public ResponseEntity<Admin> loginAdmin(@PathVariable String email, @PathVariable String password) {
        return service.loginAdmin(email, password)
                .map(_admin -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_admin))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

}
