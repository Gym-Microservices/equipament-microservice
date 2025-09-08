package com.gym.equipament_microservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "equipment")
@Schema(description = "Entity that represents gym equipment")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the equipment", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Name of the equipment", example = "Electric Treadmill", required = true)
    private String name;

    @Column
    @Schema(description = "Description of the equipment", example = "Electric treadmill with LCD screen")
    private String description;

    @Column(nullable = false)
    @Schema(description = "Total quantity of the equipment", example = "5", required = true)
    private int quantity;

    @Column(name = "available_quantity")
    @Schema(description = "Available quantity of the equipment", example = "3")
    private int availableQuantity;
}
