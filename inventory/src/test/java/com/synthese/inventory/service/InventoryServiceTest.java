package com.synthese.inventory.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synthese.inventory.model.Item;
import com.synthese.inventory.model.OrderItem;
import com.synthese.inventory.repository.ItemRepository;
import org.bson.types.Binary;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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
    private List<Item> expectedItems;
    private Item givenItem;
    private Binary expectedImage;
    private List<OrderItem> givenOrderItems;

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

    @Test
    //@Disabled
    public void testUpdateItemsQuantity() throws Exception {
        //Arrange
        givenOrderItems = getListOfOrderItems();
        expectedItem = getItemWithID();

        when(itemRepository.save(any())).thenReturn(expectedItem);
        when(itemRepository.findByIdWithoutImage(any())).thenReturn(Optional.ofNullable(expectedItem));
        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(expectedItem));
        //Act
        Optional<List<Item>> optionalItem = service.updateItemsQuantity(givenOrderItems);

        //Assert
        assertThat(optionalItem.isEmpty()).isTrue();
    }

    @Test
    //@Disabled
    public void getAllItemsFromCategory() throws IOException {
        //Arrange
        expectedItems = getListOfItems();
        when(itemRepository.findAllByCategoryOrderByCreationDateDesc(CATEGORY_OTHER))
                .thenReturn(expectedItems);

        //Act
        final Optional<List<Item>> optionalItems =
                service.getAllItemsFromCategory(CATEGORY_OTHER);

        //Assert
        List<Item> actualItems = optionalItems.orElse(null);

        assertThat(optionalItems.isPresent()).isTrue();
        assertThat(actualItems.size()).isEqualTo(expectedItems.size());
    }

    @Test
    //@Disabled
    public void getVisibleItemsFromCategory() throws IOException {
        //Arrange
        expectedItems = getListOfItems();
        when(itemRepository.findAllVisibleByCategoryOrderByCreationDateDesc(CATEGORY_OTHER))
                .thenReturn(expectedItems);

        //Act
        final Optional<List<Item>> optionalItems =
                service.getVisibleItemsFromCategory(CATEGORY_OTHER);

        //Assert
        List<Item> actualItems = optionalItems.orElse(null);

        assertThat(optionalItems.isPresent()).isTrue();
        assertThat(actualItems.size()).isEqualTo(expectedItems.size());
    }


    @Test
    //@Disabled
    public void testGetImage() throws Exception {
        //Arrange
        expectedImage = getImage();
        expectedItem = getItemWithID();

        when(itemRepository.findById(ID))
                .thenReturn(Optional.ofNullable(expectedItem));

        //Act
        final Optional<byte[]> optionalImage =
                service.getImage(ID);

        //Assert
        byte[] actualImage = optionalImage.orElse(null);

        assertThat(actualImage).isNotNull();
        assertThat(actualImage).isEqualTo(expectedImage.getData());
    }

}