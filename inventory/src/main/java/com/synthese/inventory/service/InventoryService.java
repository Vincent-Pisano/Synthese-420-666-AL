package com.synthese.inventory.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synthese.inventory.model.Item;
import com.synthese.inventory.model.OrderItem;
import com.synthese.inventory.repository.ItemRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final Logger logger;
    private final ItemRepository itemRepository;

    InventoryService(ItemRepository itemRepository) {
        this.logger = LoggerFactory.getLogger(InventoryService.class);
        this.itemRepository = itemRepository;
    }

    public Optional<Item> saveItem(String itemJSON, MultipartFile multipartFile) {
        Optional<Item> optionalItem = Optional.empty();

        try {
            Item item = getItem(itemJSON, multipartFile);
            if (item.getId() != null && item.getImage() == null)
                itemRepository.findById(item.getId()).ifPresent(item1 -> item.setImage(item1.getImage()));
            optionalItem = Optional.of(itemRepository.save(item));
        } catch (DuplicateKeyException exception) {
            logger.error("A duplicated key was found in InventoryService.saveItem : " + exception.getMessage());
        }
        return optionalItem;
    }

    private Item getItem(String itemJSON, MultipartFile multipartFile){
        Item item = mapItem(itemJSON);
        if (item != null && multipartFile != null) {
            item.setImage(extractImage(multipartFile));
        }
        return item;
    }

    private Item mapItem(String itemJSON) {
        Item item = null;

        try {
            item = new ObjectMapper().readValue(itemJSON, Item.class);
        } catch (IOException e) {
            logger.error("Couldn't map the string itemJSON to Item.class at " +
                    "mapItem in InventoryService.mapItem : " + e.getMessage());
        }
        return item;
    }

    private Binary extractImage(MultipartFile multipartFile) {
        Binary image = null;
        try {
            image = new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes());
        } catch (IOException e) {
            logger.error("Couldn't extract the image from multipartFile at " +
                    "getItem in InventoryService.getItem : " + e.getMessage());
        }
        return image;
    }


    public Optional<List<Item>> updateItemsQuantity(List<OrderItem> orderItems) {
        Optional<List<Item>> optionalItems = Optional.ofNullable(getItemsWithoutValidQuantity(orderItems));
        if (optionalItems.isEmpty()) {
            updateQuantityOfItems(orderItems);
        }
        return optionalItems;
    }

    private List<Item> getItemsWithoutValidQuantity(List<OrderItem> orderItems) {
        return orderItems.stream()
            .map(_orderItem -> {
                Item item = _orderItem.getItem();
                return itemRepository.findByIdWithoutImage(item.getId())
                        .filter(_item -> _item.getQuantity() - _orderItem.getQuantity() < 0).orElse(null);
            })
            .filter(Objects::nonNull)
            .collect(Collectors.collectingAndThen(Collectors.toList(), items -> !items.isEmpty() ? items:null));
    }

    private void updateQuantityOfItems(List<OrderItem> orderItems) {
        orderItems.forEach(_orderItem -> {
            Item item = _orderItem.getItem();
            itemRepository.findById(item.getId())
                .map(_item -> {
                    _item.setQuantity(_item.getQuantity() - _orderItem.getQuantity());
                    return itemRepository.save(_item);
                });
        });
    }

    public Optional<List<Item>> getAllItemsFromCategory(Item.ItemCategory category) {
        List<Item> items =
                itemRepository.findAllByCategoryOrderByCreationDateDesc(category);
        return items.isEmpty() ? Optional.empty() : Optional.of(items);
    }

    public Optional<List<Item>> getVisibleItemsFromCategory(Item.ItemCategory category) {
        List<Item> items =
                itemRepository.findAllVisibleByCategoryOrderByCreationDateDesc(category);
        return items.isEmpty() ? Optional.empty() : Optional.of(items);
    }

    public Optional<byte[]> getImage(String id) {
        Optional<byte[]> image;
        Optional<Item> optionalItem = itemRepository.findById(id);
        image = optionalItem.map(item -> {
            Binary imageBinary = item.getImage();
            return imageBinary.getData();
        });
        return image;
    }
}
