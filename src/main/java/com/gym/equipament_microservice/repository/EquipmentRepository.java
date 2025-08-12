package com.gym.equipament_microservice.repository;

import com.gym.equipament_microservice.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    
    List<Equipment> findByAvailableQuantityGreaterThan(int minQuantity);
    
    List<Equipment> findByQuantityGreaterThan(int minQuantity);
}
