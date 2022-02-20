package com.synthese.inventory.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synthese.inventory.model.Item;
import com.synthese.inventory.repository.ItemRepository;
import org.bson.types.Binary;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.synthese.inventory.utils.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @InjectMocks
    private InventoryService service;

    @Mock
    private ItemRepository itemRepository;

    //global variables
    private Item expectedItem;
    private Item givenItem;

    @Test
    //@Disabled
    public void testSaveItem() throws Exception {
        //Arrange
        Binary image = getImage();
        var multipartFile = mock(MultipartFile.class);
        when(multipartFile.getBytes()).thenReturn(image.getData());

        expectedItem = getItemWithID();
        givenItem = getItemWithoutID();
        givenItem.setImage(null);
        String givenItemJSON = new ObjectMapper().writeValueAsString(givenItem);

        when(itemRepository.save(any())).thenReturn(expectedItem);
        //Act
        Optional<Item> optionalItem = service.saveItem(givenItemJSON, multipartFile);

        //Assert
        Item actualItem = optionalItem.orElse(null);

        assertThat(optionalItem.isPresent()).isTrue();
        assertThat(actualItem.getImage()).isNotNull();
        assertThat(actualItem.getId()).isEqualTo(expectedItem.getId());
    }

}