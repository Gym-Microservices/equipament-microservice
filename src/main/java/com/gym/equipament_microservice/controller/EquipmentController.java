package com.gym.equipament_microservice.controller;

import com.gym.equipament_microservice.model.Equipment;
import com.gym.equipament_microservice.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipment")
@CrossOrigin(origins = "*")
public class EquipmentController {
    
    @Autowired
    private EquipmentService equipmentService;
    
    @PostMapping
    public ResponseEntity<Equipment> addEquipment(@RequestBody Equipment equipment) {
        try {
            Equipment addedEquipment = equipmentService.addEquipment(equipment);
            return ResponseEntity.ok(addedEquipment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Equipment>> getAllEquipment() {
        List<Equipment> equipment = equipmentService.getAllEquipment();
        return ResponseEntity.ok(equipment);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Equipment>> getActiveEquipment() {
        List<Equipment> activeEquipment = equipmentService.getActiveEquipment();
        return ResponseEntity.ok(activeEquipment);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable Long id) {
        Optional<Equipment> equipment = equipmentService.getEquipmentById(id);
        return equipment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Equipment>> getEquipmentByCategory(@PathVariable String category) {
        List<Equipment> equipment = equipmentService.getEquipmentByCategory(category);
        return ResponseEntity.ok(equipment);
    }
    
    @GetMapping("/available/{minQuantity}")
    public ResponseEntity<List<Equipment>> getAvailableEquipment(@PathVariable int minQuantity) {
        List<Equipment> equipment = equipmentService.getAvailableEquipment(minQuantity);
        return ResponseEntity.ok(equipment);
    }
    
    @GetMapping("/maintenance/needed")
    public ResponseEntity<List<Equipment>> getEquipmentNeedingMaintenance() {
        List<Equipment> equipment = equipmentService.getEquipmentNeedingMaintenance();
        return ResponseEntity.ok(equipment);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Equipment> updateEquipment(@PathVariable Long id, @RequestBody Equipment equipmentDetails) {
        try {
            Equipment updatedEquipment = equipmentService.updateEquipment(id, equipmentDetails);
            return ResponseEntity.ok(updatedEquipment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{id}/reserve/{quantity}")
    public ResponseEntity<Equipment> reserveEquipment(@PathVariable Long id, @PathVariable int quantity) {
        try {
            Equipment equipment = equipmentService.reserveEquipment(id, quantity);
            return ResponseEntity.ok(equipment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{id}/return/{quantity}")
    public ResponseEntity<Equipment> returnEquipment(@PathVariable Long id, @PathVariable int quantity) {
        try {
            Equipment equipment = equipmentService.returnEquipment(id, quantity);
            return ResponseEntity.ok(equipment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.ok().build();
    }
}
