package com.synthese.shipping.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.synthese.shipping.model.Order;
import com.synthese.shipping.service.ShippingService;
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
import static com.synthese.shipping.utils.Utils.*;
import static com.synthese.shipping.utils.Utils.ShippingControllerUrl.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(ShippingController.class)
class ShippingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShippingService service;

    //global variables
    private Order expectedOrder;

    @Test
    //@Disabled
    void testHandleOrder() throws Exception {
        //Arrange
        expectedOrder = getOrderWithIDAndOrderItems();

        when(service.handleOrder(ID))
                .thenReturn(java.util.Optional.ofNullable(expectedOrder));

        //Act
        MvcResult result = mockMvc.perform(get(URL_HANDLE_ORDER + ID)
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