package com.example.boilerplate.config;

import com.example.boilerplate.model.Item;
import com.example.boilerplate.repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(ItemRepository itemRepository) {
        return args -> {
            // Only initialize if the repository is empty
            if (itemRepository.count() == 0) {
                itemRepository.save(Item.builder()
                        .name("Sample Item 1")
                        .description("This is the first sample item")
                        .active(true)
                        .build());
                        
                itemRepository.save(Item.builder()
                        .name("Sample Item 2")
                        .description("This is the second sample item")
                        .active(true)
                        .build());
                        
                itemRepository.save(Item.builder()
                        .name("Inactive Item")
                        .description("This is an inactive sample item")
                        .active(false)
                        .build());
                        
                System.out.println("Sample data initialized");
            }
        };
    }
}
