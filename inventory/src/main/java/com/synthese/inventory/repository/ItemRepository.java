package com.synthese.inventory.repository;

import com.synthese.inventory.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {
}
