package com.example.boilerplate.repository;

import com.example.boilerplate.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    
    List<Item> findByActiveTrue();
    
    List<Item> findByNameContainingIgnoreCase(String name);
}
