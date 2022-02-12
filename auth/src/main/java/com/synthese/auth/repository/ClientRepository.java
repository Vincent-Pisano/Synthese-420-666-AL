package com.synthese.auth.repository;

import com.synthese.auth.model.Admin;
import com.synthese.auth.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {
    Optional<Client> findByEmailAndPasswordAndIsDisabledFalse(String username, String password);
}
