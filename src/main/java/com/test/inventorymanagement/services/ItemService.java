package com.test.inventorymanagement.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.test.inventorymanagement.exceptions.ItemNotExistsException;
import com.test.inventorymanagement.models.ItemModel;
import com.test.inventorymanagement.repository.ItemRepository;

@Service
public class ItemService {
	@Autowired
    ItemRepository itemRepository;

    public ItemModel save(ItemModel itemModel) throws ItemNotExistsException{
        ItemModel existingModel = itemRepository.findByItemName(itemModel.getItemName());
        if(existingModel!=null){
            throw new ItemNotExistsException("Item with name" +itemModel.getItemName() +"already exists");
        }
        return itemRepository.save(itemModel);
       // return itemRepository.save(new ItemModel(itemModel.getItemId(),itemModel.getItemName(), itemModel.getDescription()));
    }

   public ItemModel getItem(String itemName){
         return itemRepository.findByItemName(itemName);
    }

   public ItemModel getItem(Long itemId){
       return itemRepository.findById(itemId).orElseGet(null);
    }

   public void deleteItem(Long itemId){
        itemRepository.deleteById(itemId);
        System.out.println("Item Deleted");
    }

    public List<ItemModel> fetchAll(){
        //return itemRepository.findAll();
        List<ItemModel> result = new ArrayList<ItemModel>();
        itemRepository.findAll().forEach(result::add);
        System.out.println("Item List has  "+result.size()+"items");
        return result;
    }
    public ItemModel update(ItemModel itemModel) {
    	 return itemRepository.save(itemModel);
    }
   public List<ItemModel> insertAll(List<ItemModel> itemList){
    	return (List<ItemModel>)itemRepository.saveAll(itemList);
    	
    }
   public void deleteAllById(List<Long> itemids) {
	    itemRepository.deleteAllById(itemids);
   }
   public boolean updateAll(List<ItemModel> itemList) {
       try {
    	   itemRepository.saveAll(itemList);
    	   
           return true;
       } catch (Exception e) {
           return false;
       }
   }
}