package com.synthese.order.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.synthese.order.model.Order;
import com.synthese.order.service.OrderService;
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

import static org.mockito.Mockito.when;
import static com.synthese.order.utils.Utils.*;
import static com.synthese.order.utils.Utils.OrderControllerUrl.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService service;

    //global variables
    private Order expectedOrder;
    private Order givenOrder;

    @Test
    //@Disabled
    public void testSaveOrder() throws Exception {
        //Arrange
        expectedOrder = getOrderWithID();
        givenOrder = getOrderWithoutID();
        when(service.saveOrder(givenOrder)).thenReturn(java.util.Optional.ofNullable(expectedOrder));

        //Act
        MvcResult result = mockMvc.perform(post(URL_SAVE_ORDER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(givenOrder))).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        var actualClient = mapper.readValue(response.getContentAsString(), Order.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualClient).isEqualTo(expectedOrder);
    }

    @Test
    //@Disabled
    public void testGetWaitingOrder() throws Exception {
        //Arrange
        expectedOrder = getOrderWithID();

        when(service.getWaitingOrder(ID))
                .thenReturn(java.util.Optional.ofNullable(expectedOrder));

        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_WAITING_ORDER + ID)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        var actualAdmin = mapper.readValue(response.getContentAsString(), Order.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualAdmin).isEqualTo(expectedOrder);
    }

}