package com.synthese.shipping.repository;

import com.synthese.shipping.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    @Query(value="{ 'isDisabled' : false, 'status': 'CONFIRMED'}")
    List<Order> findAllByStatusIsConfirmedAndIsDisabledFalse();

}
