package com.example.boilerplate.controller;

import com.example.boilerplate.model.Item;
import com.example.boilerplate.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    private Item item1;
    private Item item2;
    private List<Item> items;

    @BeforeEach
    void setUp() {
        item1 = Item.builder()
                .id(1L)
                .name("Test Item 1")
                .description("Test Description 1")
                .active(true)
                .build();

        item2 = Item.builder()
                .id(2L)
                .name("Test Item 2")
                .description("Test Description 2")
                .active(false)
                .build();

        items = Arrays.asList(item1, item2);
    }

    @Test
    void getAllItems() {
        when(itemService.getAllItems()).thenReturn(items);
        
        ResponseEntity<List<Item>> response = itemController.getAllItems(false);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(itemService).getAllItems();
    }

    @Test
    void getActiveItems() {
        List<Item> activeItems = Arrays.asList(item1);
        when(itemService.getActiveItems()).thenReturn(activeItems);
        
        ResponseEntity<List<Item>> response = itemController.getAllItems(true);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(itemService).getActiveItems();
    }

    @Test
    void getItemById_Found() {
        when(itemService.getItemById(1L)).thenReturn(Optional.of(item1));
        
        ResponseEntity<Item> response = itemController.getItemById(1L);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Item 1", response.getBody().getName());
        verify(itemService).getItemById(1L);
    }

    @Test
    void getItemById_NotFound() {
        when(itemService.getItemById(anyLong())).thenReturn(Optional.empty());
        
        ResponseEntity<Item> response = itemController.getItemById(999L);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(itemService).getItemById(999L);
    }

    @Test
    void createItem() {
        when(itemService.saveItem(any(Item.class))).thenReturn(item1);
        
        ResponseEntity<Item> response = itemController.createItem(item1);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Test Item 1", response.getBody().getName());
        verify(itemService).saveItem(item1);
    }

    @Test
    void updateItem_Success() {
        Item updatedItem = Item.builder()
                .id(1L)
                .name("Updated Item")
                .description("Updated Description")
                .active(true)
                .build();
                
        when(itemService.getItemById(1L)).thenReturn(Optional.of(item1));
        when(itemService.saveItem(any(Item.class))).thenReturn(updatedItem);
        
        ResponseEntity<Item> response = itemController.updateItem(1L, updatedItem);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Item", response.getBody().getName());
        verify(itemService).getItemById(1L);
        verify(itemService).saveItem(any(Item.class));
    }

    @Test
    void updateItem_NotFound() {
        when(itemService.getItemById(anyLong())).thenReturn(Optional.empty());
        
        ResponseEntity<Item> response = itemController.updateItem(999L, item1);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(itemService).getItemById(999L);
        verify(itemService, never()).saveItem(any(Item.class));
    }

    @Test
    void deleteItem_Success() {
        when(itemService.getItemById(1L)).thenReturn(Optional.of(item1));
        
        ResponseEntity<Void> response = itemController.deleteItem(1L);
        
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(itemService).getItemById(1L);
        verify(itemService).deleteItem(1L);
    }

    @Test
    void deleteItem_NotFound() {
        when(itemService.getItemById(anyLong())).thenReturn(Optional.empty());
        
        ResponseEntity<Void> response = itemController.deleteItem(999L);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(itemService).getItemById(999L);
        verify(itemService, never()).deleteItem(anyLong());
    }
}
