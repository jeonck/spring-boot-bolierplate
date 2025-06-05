package com.example.boilerplate.controller;

import com.example.boilerplate.model.Item;
import com.example.boilerplate.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@Tag(name = "Item Management", description = "Operations related to item management including CRUD operations and search functionality")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(
        summary = "Get all items",
        description = "Retrieve all items from the database. Optionally filter by active status."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved items",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Item[].class)
            )
        )
    })
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems(
            @Parameter(description = "Filter to show only active items", example = "false")
            @RequestParam(required = false, defaultValue = "false") boolean activeOnly) {
        
        List<Item> items = activeOnly ? 
                itemService.getActiveItems() : 
                itemService.getAllItems();
                
        return ResponseEntity.ok(items);
    }

    @Operation(
        summary = "Get item by ID",
        description = "Retrieve a specific item by its unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Item found and returned successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Item.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(
            @Parameter(description = "ID of the item to retrieve", example = "1", required = true)
            @PathVariable Long id) {
        return itemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Create a new item",
        description = "Create a new item with the provided details"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Item created successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Item.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Item> createItem(
            @Parameter(description = "Item details to create", required = true)
            @Valid @RequestBody Item item) {
        Item savedItem = itemService.saveItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

    @Operation(
        summary = "Update an existing item",
        description = "Update an existing item with new details"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Item updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Item.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Item not found", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(
            @Parameter(description = "ID of the item to update", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated item details", required = true)
            @Valid @RequestBody Item itemDetails) {
        
        return itemService.getItemById(id)
                .map(existingItem -> {
                    existingItem.setName(itemDetails.getName());
                    existingItem.setDescription(itemDetails.getDescription());
                    existingItem.setActive(itemDetails.isActive());
                    
                    Item updatedItem = itemService.saveItem(existingItem);
                    return ResponseEntity.ok(updatedItem);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Delete an item",
        description = "Delete an item by its ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Item deleted successfully", content = @Content),
        @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(
            @Parameter(description = "ID of the item to delete", example = "1", required = true)
            @PathVariable Long id) {
        return itemService.getItemById(id)
                .map(item -> {
                    itemService.deleteItem(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Search items",
        description = "Search for items by name (case-insensitive)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Search completed successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Item[].class)
            )
        )
    })
    @GetMapping("/search")
    public ResponseEntity<List<Item>> searchItems(
            @Parameter(description = "Search query to find items by name", example = "sample", required = true)
            @RequestParam String query) {
        List<Item> items = itemService.searchItems(query);
        return ResponseEntity.ok(items);
    }
}
