package com.synthese.order.repository;

import com.synthese.order.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    @Query(value="{ 'isDisabled' : false, 'status': { $in: ['WAITING']}, 'client.id': ?0}")
    Optional<Order> findByIdClientAndStatusWaitingAndIsDisabledFalse(String idClient);

    @Query(value="{ 'isDisabled' : false, 'status': { $in: ['CONFIRMED', 'SENT']}, 'client.id': ?0}")
    List<Order> findAllByClientIdAndIsDisabledFalse(String idClient);

}
