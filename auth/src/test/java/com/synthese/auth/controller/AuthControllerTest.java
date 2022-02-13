package com.synthese.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synthese.auth.model.Admin;
import com.synthese.auth.model.Client;
import com.synthese.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.when;
import static com.synthese.auth.utils.Utils.*;
import static com.synthese.auth.utils.Utils.AuthControllerUrl.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService service;

    //global variables
    private Client expectedClient;
    private Client givenClient;
    private Admin expectedAdmin;
    private Admin givenAdmin;

    @Test
    //@Disabled
    public void testSignUpClient() throws Exception {
        //Arrange
        expectedClient = getClientWithID();
        givenClient = getClientWithoutID();
        when(service.signUpClient(givenClient)).thenReturn(java.util.Optional.ofNullable(expectedClient));

        //Act
        MvcResult result = mockMvc.perform(post(URL_SIGN_UP_CLIENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(givenClient))).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualClient = new ObjectMapper().readValue(response.getContentAsString(), Client.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualClient).isEqualTo(expectedClient);
    }

    @Test
    //@Disabled
    public void testLoginClient() throws Exception {
        //Arrange
        expectedClient = getClientWithID();
        givenClient = getClientWithoutID();
        when(service.loginClient(givenClient.getEmail(), givenClient.getPassword()))
                .thenReturn(java.util.Optional.ofNullable(expectedClient));

        //Act
        MvcResult result = mockMvc.perform(get(URL_LOGIN_CLIENT +
                givenClient.getEmail() + "/" + givenClient.getPassword())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualClient = new ObjectMapper().readValue(response.getContentAsString(), Client.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualClient).isEqualTo(expectedClient);
    }

    @Test
    //@Disabled
    public void testLoginAdmin() throws Exception {
        //Arrange
        expectedAdmin = getAdminWithID();
        givenAdmin = getAdminWithoutID();
        when(service.loginAdmin(givenAdmin.getEmail(), givenAdmin.getPassword()))
                .thenReturn(java.util.Optional.ofNullable(expectedAdmin));

        //Act
        MvcResult result = mockMvc.perform(get(URL_LOGIN_ADMIN +
                givenAdmin.getEmail() + "/" + givenAdmin.getPassword())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualAdmin = new ObjectMapper().readValue(response.getContentAsString(), Admin.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualAdmin).isEqualTo(expectedAdmin);
    }

}