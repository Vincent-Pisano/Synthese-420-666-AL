package com.synthese.inventory.controller;

import com.synthese.inventory.model.Item;
import com.synthese.inventory.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
                                                    @RequestPart(name = REQUEST_PART_IMAGE) MultipartFile multipartFile) {
        return service.saveItem(itemJSON, multipartFile)
                .map(_item -> ResponseEntity.status(HttpStatus.CREATED).body(_item))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

}
