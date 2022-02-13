package com.synthese.auth.service;

import com.synthese.auth.model.Admin;
import com.synthese.auth.model.Client;
import com.synthese.auth.repository.AdminRepository;
import com.synthese.auth.repository.ClientRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static com.synthese.auth.utils.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService service;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AdminRepository adminRepository;

    //global variables
    private Client expectedClient;
    private Client givenClient;
    private Admin expectedAdmin;
    private Admin givenAdmin;

    @Test
    //@Disabled
    public void testSignUpClient() {
        //Arrange
        expectedClient = getClientWithID();
        givenClient = getClientWithoutID();

        when(clientRepository.save(givenClient)).thenReturn(expectedClient);

        //Act
        final Optional<Client> optionalClient = service.signUpClient(givenClient);

        //Assert
        Client actualClient = optionalClient.orElse(null);

        assertThat(optionalClient.isPresent()).isTrue();
        assertThat(actualClient).isEqualTo(expectedClient);
    }

    @Test
    //@Disabled
    public void testLoginClient() {
        //Arrange
        expectedClient = getClientWithID();
        givenClient = getClientWithoutID();

        when(clientRepository.findByEmailAndPasswordAndIsDisabledFalse(givenClient.getEmail(), givenClient.getPassword()))
                .thenReturn(Optional.ofNullable(expectedClient));

        //Act
        final Optional<Client> optionalClient = service.loginClient(givenClient.getEmail(), givenClient.getPassword());

        //Assert
        Client actualClient = optionalClient.orElse(null);

        assertThat(optionalClient.isPresent()).isTrue();
        assertThat(actualClient).isEqualTo(expectedClient);
    }

    @Test
    //@Disabled
    public void testLoginAdmin() {
        //Arrange
        expectedAdmin = getAdminWithID();
        givenAdmin = getAdminWithoutID();

        when(adminRepository.findByEmailAndPasswordAndIsDisabledFalse(givenAdmin.getEmail(), givenAdmin.getPassword()))
                .thenReturn(Optional.ofNullable(expectedAdmin));

        //Act
        final Optional<Admin> optionalAdmin = service.loginAdmin(givenAdmin.getEmail(), givenAdmin.getPassword());

        //Assert
        Admin actualAdmin = optionalAdmin.orElse(null);

        assertThat(optionalAdmin.isPresent()).isTrue();
        assertThat(actualAdmin).isEqualTo(expectedAdmin);
    }

}