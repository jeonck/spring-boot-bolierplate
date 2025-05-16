package com.example.boilerplate.service;

import com.example.boilerplate.model.Item;
import com.example.boilerplate.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

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
        when(itemRepository.findAll()).thenReturn(items);
        
        List<Item> result = itemService.getAllItems();
        
        assertEquals(2, result.size());
        assertEquals("Test Item 1", result.get(0).getName());
        assertEquals("Test Item 2", result.get(1).getName());
        verify(itemRepository).findAll();
    }

    @Test
    void getActiveItems() {
        List<Item> activeItems = Arrays.asList(item1);
        when(itemRepository.findByActiveTrue()).thenReturn(activeItems);
        
        List<Item> result = itemService.getActiveItems();
        
        assertEquals(1, result.size());
        assertEquals("Test Item 1", result.get(0).getName());
        assertTrue(result.get(0).isActive());
        verify(itemRepository).findByActiveTrue();
    }

    @Test
    void getItemById_Found() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));
        
        Optional<Item> result = itemService.getItemById(1L);
        
        assertTrue(result.isPresent());
        assertEquals("Test Item 1", result.get().getName());
        verify(itemRepository).findById(1L);
    }

    @Test
    void getItemById_NotFound() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());
        
        Optional<Item> result = itemService.getItemById(999L);
        
        assertFalse(result.isPresent());
        verify(itemRepository).findById(999L);
    }

    @Test
    void saveItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(item1);
        
        Item newItem = Item.builder()
                .name("New Item")
                .description("New Description")
                .active(true)
                .build();
                
        Item result = itemService.saveItem(newItem);
        
        assertEquals("Test Item 1", result.getName());
        verify(itemRepository).save(newItem);
    }

    @Test
    void deleteItem() {
        doNothing().when(itemRepository).deleteById(anyLong());
        
        itemService.deleteItem(1L);
        
        verify(itemRepository).deleteById(1L);
    }

    @Test
    void searchItems() {
        when(itemRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(items);
        
        List<Item> result = itemService.searchItems("Test");
        
        assertEquals(2, result.size());
        verify(itemRepository).findByNameContainingIgnoreCase("Test");
    }
}
