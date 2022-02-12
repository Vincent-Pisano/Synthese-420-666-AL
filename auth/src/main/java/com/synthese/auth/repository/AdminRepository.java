package com.synthese.auth.repository;

import com.synthese.auth.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findByEmailAndPasswordAndIsDisabledFalse(String username, String password);
}
