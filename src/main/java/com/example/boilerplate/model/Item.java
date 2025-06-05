package com.example.boilerplate.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Item entity representing a manageable item in the system")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the item", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Schema(description = "Name of the item", example = "Sample Item", required = true)
    private String name;

    @Schema(description = "Detailed description of the item", example = "This is a sample item for demonstration purposes")
    private String description;

    @Schema(description = "Indicates whether the item is active or not", example = "true", defaultValue = "true")
    private boolean active = true;
}
