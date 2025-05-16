package com.example.boilerplate.repository;

import com.example.boilerplate.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void findByActiveTrue() {
        // Create test data
        Item activeItem = Item.builder()
                .name("Active Item")
                .description("This item is active")
                .active(true)
                .build();
                
        Item inactiveItem = Item.builder()
                .name("Inactive Item")
                .description("This item is inactive")
                .active(false)
                .build();
                
        itemRepository.save(activeItem);
        itemRepository.save(inactiveItem);
        
        // Test the method
        List<Item> activeItems = itemRepository.findByActiveTrue();
        
        // Assertions
        assertNotNull(activeItems);
        assertFalse(activeItems.isEmpty());
        assertEquals(1, activeItems.size());
        assertTrue(activeItems.get(0).isActive());
        assertEquals("Active Item", activeItems.get(0).getName());
    }

    @Test
    void findByNameContainingIgnoreCase() {
        // Create test data
        Item item1 = Item.builder()
                .name("Test Item")
                .description("Test Description")
                .active(true)
                .build();
                
        Item item2 = Item.builder()
                .name("Another test Item")
                .description("Another Description")
                .active(true)
                .build();
                
        Item item3 = Item.builder()
                .name("Something Else")
                .description("Different Description")
                .active(true)
                .build();
                
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        
        // Test the method with different case
        List<Item> testItems = itemRepository.findByNameContainingIgnoreCase("test");
        
        // Assertions
        assertNotNull(testItems);
        assertEquals(2, testItems.size());
        
        // Check that it's case insensitive
        List<Item> testItemsUpperCase = itemRepository.findByNameContainingIgnoreCase("TEST");
        assertEquals(2, testItemsUpperCase.size());
        
        // Check with a term that should return no results
        List<Item> noResults = itemRepository.findByNameContainingIgnoreCase("nonexistent");
        assertTrue(noResults.isEmpty());
    }
}
