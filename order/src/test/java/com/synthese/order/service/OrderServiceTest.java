package com.synthese.order.service;

import com.synthese.order.model.Item;
import com.synthese.order.model.Order;
import com.synthese.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static com.synthese.order.utils.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService service;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    //global variables
    private Order expectedOrder;
    private Order givenOrder;

    @Test
    //@Disabled
    public void testSaveOrder() throws Exception {
        //Arrange
        expectedOrder = getOrderWithID();
        givenOrder = getOrderWithoutID();
        when(orderRepository.save(givenOrder)).thenReturn(expectedOrder);

        //Act
        final Optional<Order> optionalOrder = service.saveOrder(givenOrder);

        //Assert
        Order actualOrder = optionalOrder.orElse(null);

        assertThat(optionalOrder.isPresent()).isTrue();
        assertThat(actualOrder).isEqualTo(expectedOrder);

    }

    @Test
    //@Disabled
    public void testConfirmOrder() throws Exception {
        //Arrange
        givenOrder = getOrderWithIDAndOrderItems();
        when(orderRepository.save(givenOrder)).thenReturn(givenOrder);
        when(orderRepository.findById(any())).thenReturn(Optional.ofNullable(givenOrder));

        //Act
        final Optional<List<Item>> optionalItems = service.confirmOrder(givenOrder);

        //Assert
        assertThat(optionalItems.isEmpty()).isTrue();
    }

    @Test
    //@Disabled
    public void testGetWaitingOrder() throws Exception {
        //Arrange
        expectedOrder = getOrderWithID();

        when(service.getWaitingOrder(ID))
                .thenReturn(java.util.Optional.ofNullable(expectedOrder));

        //Act
        final Optional<Order> optionalOrder = service.getWaitingOrder(ID);

        //Assert
        Order actualOrder = optionalOrder.orElse(null);

        assertThat(optionalOrder.isPresent()).isTrue();
        assertThat(actualOrder).isEqualTo(expectedOrder);

    }
}