package com.synthese.shipping.service;

import com.synthese.shipping.model.Order;
import com.synthese.shipping.repository.OrderRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static com.synthese.shipping.utils.Utils.*;

@ExtendWith(MockitoExtension.class)
class ShippingServiceTest {

    @InjectMocks
    private ShippingService service;

    @Mock
    private OrderRepository orderRepository;

    //global variables
    private Order expectedOrder;
    private List<Order> expectedOrders;

    @Test
    //@Disabled
    void testHandleOrder() throws IOException {
        //Arrange
        expectedOrder = getOrderWithIDAndOrderItems();
        when(orderRepository.findById(ID)).thenReturn(Optional.ofNullable(expectedOrder));

        //Act
        final Optional<Order> optionalOrder = service.handleOrder(ID);

        //Assert
        Order actualOrder = optionalOrder.orElse(null);

        assertThat(optionalOrder.isPresent()).isTrue();
        assertThat(actualOrder).isEqualTo(expectedOrder);
    }

    @Test
    //@Disabled
    void testCheckForConfirmedOrders() throws IOException {
        //Arrange
        expectedOrders = List.of(getOrderWithIDAndOrderItems());
        expectedOrders.forEach(order -> {
            order.setStatus(Order.OrderStatus.CONFIRMED);
        });
        when(orderRepository.findAllByStatusIsConfirmedAndIsDisabledFalse()).thenReturn(expectedOrders);

        //Act
        service.checkForConfirmedOrders();

        //Assert
        expectedOrders.forEach(order -> {
            assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.SENT);
        });
    }
}