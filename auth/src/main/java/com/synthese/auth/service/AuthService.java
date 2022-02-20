package com.synthese.auth.service;

import com.synthese.auth.model.Admin;
import com.synthese.auth.model.Client;
import com.synthese.auth.repository.AdminRepository;
import com.synthese.auth.repository.ClientRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class AuthService {

    private final Logger logger;
    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;

    AuthService(AdminRepository adminRepository, ClientRepository clientRepository) {
        this.logger = LoggerFactory.getLogger(AuthService.class);
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
    }

    public Optional<Client> signUpClient(Client client) {
        Optional<Client> optionalClient = Optional.empty();
        try {
            optionalClient = Optional.of(clientRepository.save(client));
        } catch (DuplicateKeyException exception) {
            logger.error("A duplicated key was found in signUp (Client) : " + exception.getMessage());
        }
        return optionalClient;
    }

    public Optional<Client> loginClient(String email, String password) {
        return clientRepository.findByEmailAndPasswordAndIsDisabledFalse(email, password);
    }

    public Optional<Admin> loginAdmin(String email, String password) {
        return adminRepository.findByEmailAndPasswordAndIsDisabledFalse(email, password);
    }
}
