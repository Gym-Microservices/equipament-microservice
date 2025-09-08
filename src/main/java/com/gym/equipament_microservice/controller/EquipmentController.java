package com.gym.equipament_microservice.controller;

import com.gym.equipament_microservice.model.Equipment;
import com.gym.equipament_microservice.service.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipment")
@CrossOrigin(origins = "*")
@Tag(name = "Equipment", description = "API for gym equipment management")
@SecurityRequirement(name = "bearer-key")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @PostMapping
    @Operation(summary = "Add equipment", description = "Registers new equipment in the gym")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment added successfully"),
            @ApiResponse(responseCode = "400", description = "Error in input data")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Equipment> addEquipment(@RequestBody Equipment equipment) {
        try {
            Equipment addedEquipment = equipmentService.addEquipment(equipment);
            return ResponseEntity.ok(addedEquipment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @Operation(summary = "Get all equipment", description = "Returns a list of all available equipment")
    @ApiResponse(responseCode = "200", description = "Equipment list retrieved successfully")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER') or hasRole('MEMBER')")
    public ResponseEntity<List<Equipment>> getAllEquipment() {
        List<Equipment> equipment = equipmentService.getAllEquipment();
        return ResponseEntity.ok(equipment);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get equipment by ID", description = "Returns a specific equipment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment found"),
            @ApiResponse(responseCode = "404", description = "Equipment not found")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER') or hasRole('MEMBER')")
    public ResponseEntity<Equipment> getEquipmentById(
            @Parameter(description = "Equipment ID") @PathVariable Long id) {
        Optional<Equipment> equipment = equipmentService.getEquipmentById(id);
        return equipment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/available/{minQuantity}")
    @Operation(summary = "Get available equipment", description = "Returns available equipment with specified minimum quantity")
    @ApiResponse(responseCode = "200", description = "Available equipment list retrieved successfully")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER') or hasRole('MEMBER')")
    public ResponseEntity<List<Equipment>> getAvailableEquipment(
            @Parameter(description = "Minimum available quantity") @PathVariable int minQuantity) {
        List<Equipment> equipment = equipmentService.getAvailableEquipment(minQuantity);
        return ResponseEntity.ok(equipment);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update equipment", description = "Updates the data of existing equipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Equipment not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Equipment> updateEquipment(
            @Parameter(description = "Equipment ID") @PathVariable Long id,
            @RequestBody Equipment equipmentDetails) {
        try {
            Equipment updatedEquipment = equipmentService.updateEquipment(id, equipmentDetails);
            return ResponseEntity.ok(updatedEquipment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/reserve/{quantity}")
    @Operation(summary = "Reserve equipment", description = "Reserves a specific quantity of equipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment reserved successfully"),
            @ApiResponse(responseCode = "400", description = "Error reserving equipment")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    public ResponseEntity<Equipment> reserveEquipment(
            @Parameter(description = "Equipment ID") @PathVariable Long id,
            @Parameter(description = "Quantity to reserve") @PathVariable int quantity) {
        try {
            Equipment equipment = equipmentService.reserveEquipment(id, quantity);
            return ResponseEntity.ok(equipment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/return/{quantity}")
    @Operation(summary = "Return equipment", description = "Returns a specific quantity of reserved equipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment returned successfully"),
            @ApiResponse(responseCode = "400", description = "Error returning equipment")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    public ResponseEntity<Equipment> returnEquipment(
            @Parameter(description = "Equipment ID") @PathVariable Long id,
            @Parameter(description = "Quantity to return") @PathVariable int quantity) {
        try {
            Equipment equipment = equipmentService.returnEquipment(id, quantity);
            return ResponseEntity.ok(equipment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete equipment", description = "Deletes equipment from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Equipment not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEquipment(@Parameter(description = "Equipment ID") @PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.ok().build();
    }
}
