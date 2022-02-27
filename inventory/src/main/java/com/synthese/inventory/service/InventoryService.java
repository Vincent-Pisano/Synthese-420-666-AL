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
import java.util.List;
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

    //pour admin
    // quand arrivé à client, ne pas oublié de supprimer les items HIDDEN
    public Optional<List<Item>> getItemsFromCategory(Item.ItemCategory category) {
        List<Item> items =
                itemRepository.findAllByCategoryOrderByCreationDateDesc(category);
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
