package com.synthese.inventory.repository;

import com.synthese.inventory.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends MongoRepository<Item, String> {

    @Query(value="{ 'category' : ?0, 'status': { $in: ['HIDDEN','VISIBLE']}}", fields="{ 'image' : 0}", sort = "{'creationDate': -1}")
    List<Item> findAllByCategoryOrderByCreationDateDesc(Item.ItemCategory category);

    @Query(value="{ '_id' : ?0}", fields="{ 'image' : 0}")
    Optional<Item> findByIdWithoutImage(String id);

    @Query(value="{ 'category' : ?0, 'status': { $in: ['VISIBLE']}}", fields="{ 'image' : 0}", sort = "{'creationDate': -1}")
    List<Item> findAllVisibleByCategoryOrderByCreationDateDesc(Item.ItemCategory category);
}
