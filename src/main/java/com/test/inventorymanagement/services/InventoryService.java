package com.test.inventorymanagement.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.inventorymanagement.exceptions.InsufficientStockExcpetion;
import com.test.inventorymanagement.exceptions.ItemNotExistsException;
import com.test.inventorymanagement.models.InventoryModel;
import com.test.inventorymanagement.repository.InventoryRepository;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

   public InventoryModel addToInventory(Long itemId,int count) throws ItemNotExistsException{
        Optional<InventoryModel> inventoryModelOptional = inventoryRepository.findById(itemId);
        InventoryModel inventoryModel = inventoryModelOptional.isPresent() ? inventoryModelOptional.get() : null;
        if(inventoryModel!=null){
            //inventoryModel.count+=count;
        	int newcount=inventoryModel.getCount()+count;
        	inventoryModel.setCount(newcount);
        	inventoryModel.setUpdatedOn(new Date(System.currentTimeMillis()));
        	 return inventoryRepository.save(inventoryModel);
        }else{
           /* inventoryModel = new InventoryModel();
           
            inventoryModel.setItemId(itemId);
            inventoryModel.setCount(count);
            inventoryModel.setUpdatedOn(new Date(System.currentTimeMillis()));*/
        	throw new ItemNotExistsException("ItemId"+itemId+" does not exist,Please create one");
        }
       
    }

  public  InventoryModel deleteFromInventory(Long itemId,int count) throws InsufficientStockExcpetion{
        Optional<InventoryModel> inventoryModelOptional = inventoryRepository.findById(itemId);
        InventoryModel inventoryModel = inventoryModelOptional.isPresent() ? inventoryModelOptional.get() : null;
        if(inventoryModel!=null && inventoryModel.getCount() >= count){
           // inventoryModel.count-=count;
        	int newcount=inventoryModel.getCount()-count;
        	inventoryModel.setCount(newcount);
         //   inventoryRepository.save(inventoryModel);
        	 return inventoryRepository.save(inventoryModel);
        }else{
            throw new InsufficientStockExcpetion("ItemId"+itemId+" doesnot have enough stock");
        }
       
    }
  public void saveInventory(InventoryModel inventoryModel) {
	   inventoryRepository.save(inventoryModel);
  }
}
