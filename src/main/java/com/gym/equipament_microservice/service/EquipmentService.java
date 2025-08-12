package com.gym.equipament_microservice.service;

import com.gym.equipament_microservice.model.Equipment;
import com.gym.equipament_microservice.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {
    
    @Autowired
    private EquipmentRepository equipmentRepository;
    
    public Equipment addEquipment(Equipment equipment) {        
        if (equipment.getAvailableQuantity() == 0) {
            equipment.setAvailableQuantity(equipment.getQuantity());
        }
        
        return equipmentRepository.save(equipment);
    }
    
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }
    
    public Optional<Equipment> getEquipmentById(Long id) {
        return equipmentRepository.findById(id);
    }
    
    public List<Equipment> getAvailableEquipment(int minQuantity) {
        return equipmentRepository.findByAvailableQuantityGreaterThan(minQuantity);
    }
    
    public Equipment updateEquipment(Long id, Equipment equipmentDetails) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found with id: " + id));
        
        equipment.setName(equipmentDetails.getName());
        equipment.setDescription(equipmentDetails.getDescription());
        equipment.setQuantity(equipmentDetails.getQuantity());
        
        return equipmentRepository.save(equipment);
    }
    
    public Equipment reserveEquipment(Long id, int quantity) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found with id: " + id));
        
        if (equipment.getAvailableQuantity() < quantity) {
            throw new RuntimeException("Not enough equipment available. Available: " + equipment.getAvailableQuantity());
        }
        
        equipment.setAvailableQuantity(equipment.getAvailableQuantity() - quantity);
        return equipmentRepository.save(equipment);
    }
    
    public Equipment returnEquipment(Long id, int quantity) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found with id: " + id));
        
        equipment.setAvailableQuantity(equipment.getAvailableQuantity() + quantity);
        if (equipment.getAvailableQuantity() > equipment.getQuantity()) {
            equipment.setAvailableQuantity(equipment.getQuantity());
        }
        
        return equipmentRepository.save(equipment);
    }
    
    public void deleteEquipment(Long id) {
        equipmentRepository.deleteById(id);
    }
}
