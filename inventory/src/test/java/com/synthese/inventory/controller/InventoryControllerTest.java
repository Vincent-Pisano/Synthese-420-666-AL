package com.synthese.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synthese.inventory.model.Item;
import com.synthese.inventory.model.OrderItem;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.assertj.core.api.Assertions.assertThat;
import static com.synthese.inventory.utils.Utils.*;
import static com.synthese.inventory.utils.Utils.InventoryControllerUrl.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService service;

    //global variables
    private Item expectedItem;
    private List<Item> expectedItems;
    private Item givenItem;
    private Binary expectedImage;
    private List<OrderItem> givenOrderItems;

    @Test
    //@Disabled
    public  void testSaveItem() throws Exception {
        //Arrange
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

    @Test
    //@Disabled
    public  void testUpdateItemsQuantity() throws Exception {
        //Arrange
        givenOrderItems = getListOfOrderItems();

        when(service.updateItemsQuantity(givenOrderItems)).thenReturn(Optional.empty());

        //Act
        MvcResult result = mockMvc.perform(post(URL_UPDATE_ITEMS_QUANTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(givenOrderItems))).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
    }

    @Test
    //@Disabled
    public void testGetAllItemsFromCategory() throws Exception {
        //Arrange
        expectedItems = getListOfItems();

        when(service.getAllItemsFromCategory(CATEGORY_OTHER))
                .thenReturn(Optional.ofNullable(expectedItems));

        //Act
        MvcResult result = mockMvc.perform(get(
                URL_GET_ALL_ITEMS_FROM_CATEGORY + CATEGORY_OTHER.name())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualItems = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualItems).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetVisibleItemsFromCategory() throws Exception {
        //Arrange
        expectedItems = getListOfItems();

        when(service.getVisibleItemsFromCategory(CATEGORY_OTHER))
                .thenReturn(Optional.ofNullable(expectedItems));

        //Act
        MvcResult result = mockMvc.perform(get(
                URL_GET_VISIBLE_ITEMS_FROM_CATEGORY + CATEGORY_OTHER.name())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualItems = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualItems).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetImage() throws Exception {
        //Arrange
        expectedImage = getImage();

        when(service.getImage(ID))
                .thenReturn(Optional.of(expectedImage.getData()));

        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_IMAGE + ID)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
    }
}