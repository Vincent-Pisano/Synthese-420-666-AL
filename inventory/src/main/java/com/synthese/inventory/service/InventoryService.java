package com.synthese.inventory.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synthese.inventory.model.Item;
import com.synthese.inventory.repository.ItemRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

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
            optionalItem = Optional.of(itemRepository.save(getItem(itemJSON, multipartFile)));
        } catch (DuplicateKeyException exception) {
            logger.error("A duplicated key was found in InventoryService.saveItem : " + exception.getMessage());
        }

        return optionalItem;
    }

    private Item getItem(String itemJSON, MultipartFile multipartFile){
        Item item = mapItem(itemJSON);
        if (item != null) {
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

}
