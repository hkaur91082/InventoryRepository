package com.test.inventorymanagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.test.inventorymanagement.models.ItemModel;

public interface ItemRepository extends CrudRepository<ItemModel,Long>{

    public ItemModel findByItemName(String itemName);
}
