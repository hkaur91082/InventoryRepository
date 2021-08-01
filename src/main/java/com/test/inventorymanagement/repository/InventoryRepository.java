package com.test.inventorymanagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.inventorymanagement.models.InventoryModel;

@Repository
public interface InventoryRepository extends CrudRepository<InventoryModel,Long> {
}