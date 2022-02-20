package com.synthese.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synthese.inventory.model.Item;
import com.synthese.inventory.service.InventoryService;
import org.bson.types.Binary;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.assertj.core.api.Assertions.assertThat;
import static com.synthese.inventory.utils.Utils.*;
import static com.synthese.inventory.utils.Utils.InventoryControllerUrl.*;

@WebMvcTest(InventoryControllerTest.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService service;

    //global variables
    private Item expectedItem;
    private Item givenItem;

    @Test
    //@Disabled
    public  void testSaveItem() throws Exception {
        //Arrange
        Binary image = getImage();
        var multipartFile = mock(MultipartFile.class);

        expectedItem = getItemWithID();
        givenItem = getItemWithoutID();
        givenItem.setImage(null);
        String givenItemJSON = new ObjectMapper().writeValueAsString(givenItem);

        when(service.saveItem(eq(givenItemJSON), any(MultipartFile.class)))
                .thenReturn(Optional.of(expectedItem));

        //Act
        HashMap<String, String> contentTypeParams = new HashMap<>();
        contentTypeParams.put("boundary", "----WebKitFormBoundary");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        MvcResult result =  mockMvc
                .perform(MockMvcRequestBuilders.multipart(URL_ADD_ITEM)
                        .file("item",givenItemJSON.getBytes())
                        .file("image", multipartFile.getBytes())
                        .contentType(mediaType)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }
}