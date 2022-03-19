package com.synthese.inventory.controller;

import com.synthese.inventory.model.Item;
import com.synthese.inventory.model.OrderItem;
import com.synthese.inventory.service.InventoryService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

import static com.synthese.inventory.utils.Utils.*;
import static com.synthese.inventory.utils.Utils.InventoryControllerUrl.*;

@RestController
@CrossOrigin(CROSS_ORIGIN_ALLOWED)
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @PostMapping(value = URL_ADD_ITEM,
            produces = APPLICATION_JSON_AND_CHARSET_UTF8,
            consumes = { MULTI_PART_FROM_DATA })
    public ResponseEntity<Item> saveItem(@RequestPart(name = REQUEST_PART_ITEM) String itemJSON,
                                                    @RequestPart(name = REQUEST_PART_IMAGE, required = false) MultipartFile multipartFile) {
        return service.saveItem(itemJSON, multipartFile)
                .map(_item -> ResponseEntity.status(HttpStatus.CREATED).body(_item))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(URL_UPDATE_ITEMS_QUANTITY)
    public ResponseEntity<List<Item>> updateItemsQuantity(@RequestBody List<OrderItem> orderItems){
        return service.updateItemsQuantity(orderItems)
                .map(_items -> ResponseEntity.status(HttpStatus.CONFLICT).body(_items))
                .orElse(ResponseEntity.status(HttpStatus.ACCEPTED).build());
    }

    @GetMapping(URL_GET_ALL_ITEMS_FROM_CATEGORY)
    public ResponseEntity<List<Item>> getAllItemsFromCategory(@PathVariable Item.ItemCategory category) {
        return service.getAllItemsFromCategory(category)
                .map(_items -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_items))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_VISIBLE_ITEMS_FROM_CATEGORY)
    public ResponseEntity<List<Item>> getVisibleItemsFromCategory(@PathVariable Item.ItemCategory category) {
        return service.getVisibleItemsFromCategory(category)
                .map(_items -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_items))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value = URL_GET_IMAGE, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<InputStreamResource> getSignature(@PathVariable String id){
        return service.getImage(id)
                .map(signature -> ResponseEntity
                        .status(HttpStatus.ACCEPTED)
                        .contentType(MediaType.IMAGE_PNG)
                        .body(new InputStreamResource(
                                new ByteArrayInputStream(signature))
                        ))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }



}
