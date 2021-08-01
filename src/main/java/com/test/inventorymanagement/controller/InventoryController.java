package com.test.inventorymanagement.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.inventorymanagement.exceptions.InsufficientStockExcpetion;
import com.test.inventorymanagement.exceptions.ItemNotExistsException;
import com.test.inventorymanagement.models.InventoryModel;
import com.test.inventorymanagement.models.ItemModel;
import com.test.inventorymanagement.services.InventoryService;
import com.test.inventorymanagement.services.ItemService;
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    ItemService itemService;
    @Autowired
    InventoryService inventoryService;

    /**
     * Check if the application is up
     * @return
     */
    @GetMapping("/ping")
    @ResponseBody
   public   Map ping(){
       // return [success:true,time:new Date(),name:"Inventory"];
    	HashMap map=new HashMap();
    	map.put("success", true);
    	map.put("time", new Date());
    	map.put("name", "Inventory");
    	return map;
    }

   /* @GetMapping("/add")
    public String addItemPage(){
        return "view";
    }*/

    /**
     * Add a new item.
     * If an item with same name already exits then return success false with error message
     * @param itemModel
     * @param modelMap
     * @return
     */
    @PostMapping("/add")
   public ResponseEntity<ItemModel> addItem(@RequestBody ItemModel itemModel){
       
        ItemModel savedModel;
      
        try {
        	itemModel.setAddedOn(new Date(System.currentTimeMillis()));
          
            // added now//
           
          
            InventoryModel inventoryModel = new InventoryModel();
            
        //    inventoryModel.setItemId(savedModel.getItemId());
            inventoryModel.setItemId(itemModel.getItemId());
            inventoryModel.setCount(1);
            inventoryModel.setUpdatedOn(new Date(System.currentTimeMillis()));
            itemModel.setInventoryByItemId(inventoryModel);
            savedModel = itemService.save(itemModel);
           // inventoryService.saveInventory(inventoryModel);
            return new ResponseEntity<>(savedModel, HttpStatus.CREATED);
        }catch (ItemNotExistsException iee){
         
            System.out.println("Exception adding item"+iee.getMessage());
            return new ResponseEntity<>( null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
      
    }

    /**
     * Delete an item based on Id
     * Throw 404 exception if not found
     * @param itemId
     * @return
     */
    @DeleteMapping("/deleteItem/{itemId}")
    @ResponseBody
   public  Map deleteItem(@PathVariable("itemId") Long itemId){
    	Map map=new HashMap();
        if(itemService.getItem(itemId)!=null){
            itemService.deleteItem(itemId);
            map.put("DeletionSuccess", "Item Deleted");
           
           
        }else{
          
        	
        	map.put("DeletionFailed", "Item Not Deleted As no Such Item");
        }
        return map;
    }

    /**
     * List all the items
     * @param modelMap
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<ItemModel>> showAll(){
     
    	List<ItemModel> items = new ArrayList<ItemModel>();
    	items=itemService.fetchAll();
    	if (items.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * View and item detail
     * @param itemId
     * @param modelMap
     * @return
     */
    @GetMapping("/view/{id}")
  public  ResponseEntity<ItemModel> viewItem(@PathVariable("id") Long itemId){
        
    	ItemModel itemData = itemService.getItem(itemId);
        
		if (itemData!=null) {
			return new ResponseEntity<>(itemData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

    /**
     * Add stock for an item.
     * Throw 404 if item is not found
     * @param itemId
     * @param count
     * @return
     */
    @PostMapping("/addStock")
    @ResponseBody
   public Map  addStock(@RequestParam("itemId") Long itemId,@RequestParam("count")Integer count){
    	InventoryModel inventory;
    	Map map=new HashMap();
    	try {
             inventory = inventoryService.addToInventory(itemId,count);
          
             map.put("itemId",itemId);
             map.put("count",inventory.getCount());
             
            
    	}catch(ItemNotExistsException e) {
    	
         
    		map.put("errorMessage", "ErrorAdding to stock"+e.getMessage());
    	}
    	return map;
    }

    /**
     * Delete stock for an item.
     * Throw 404 if item is not present
     * Return error message if count to delete > current count
     * @param itemId
     * @param count
     * @return
     */
    @DeleteMapping("/deleteStock")
    @ResponseBody
    public Map deleteStock(@RequestParam("itemId") Long itemId,@RequestParam("count")Integer count){
        
    	Map map=new HashMap();
            try {
            	InventoryModel inventoryModel = inventoryService.deleteFromInventory(itemId,count) ;
               
            	//return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            	
            	map.put("message", "Stock deleted successfully");
            	
            }catch (InsufficientStockExcpetion ise){
               
               // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            	
            	map.put("message", "Error deleting stock"+ise.getMessage()+".");
            	
            }
            
        
            return map;
    }
    @PutMapping("/updateItem/{itemId}")
	public ResponseEntity<ItemModel> updateTutorial(@PathVariable("itemId") long itemId, @RequestBody ItemModel itemModel) {
		
    	ItemModel itemData = itemService.getItem(itemId);
		if (itemData!=null) {
			
			
			itemData.setDescription(itemModel.getDescription()==null? itemData.getDescription() : itemModel.getDescription());
			
			itemData.setItemName(itemModel.getItemName()==null? itemData.getItemName() : itemModel.getItemName());
			//savedModel = itemService.save(itemData);
			return new ResponseEntity<>(itemService.update(itemData), HttpStatus.OK);
		} else {
			System.out.println("error in updation-item not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

    @PostMapping("/addItems/bulk")
    public String addItems(@RequestBody List<ItemModel> itemList) {
        if(itemList != null && !itemList.isEmpty()) {
      
        //added for inventory//
        for(ItemModel savedModel:itemList) {
        InventoryModel inventoryModel = new InventoryModel();
        
        inventoryModel.setItemId(savedModel.getItemId());
        inventoryModel.setCount(1);
        inventoryModel.setUpdatedOn(new Date(System.currentTimeMillis()));
        // added//
     savedModel.setInventoryByItemId(inventoryModel);
     //added/
     //   inventoryService.saveInventory(inventoryModel);
        }
        // added for inventory//
        List<ItemModel> itemsList=	itemService.insertAll(itemList);
            return String.format("Added %d items.", itemsList.size());
        } else {
            return "No Bulk Insert Operation Performed";
        }
    }
    @DeleteMapping("/deleteItems/bulk")
    public String deletePeople(@RequestBody List<Long> itemids) {
    	
        if(!itemids.isEmpty()) {
            itemService.deleteAllById(itemids);
            	
                return "Deleted Items";
            } else {
                return "Unable to delete items.";
            }
        }
    @PutMapping("/updateItems/bulk")
    public String updatePeople(@RequestBody  List<ItemModel> itemList) {
    	//String msg="";
        if(itemList != null) {
        	for(ItemModel imodel:itemList) {
        	ItemModel itemData = itemService.getItem(imodel.getItemId());
        	if(itemData!=null) {
itemData.setDescription(imodel.getDescription()==null? itemData.getDescription() : imodel.getDescription());
			
			itemData.setItemName(imodel.getItemName()==null? itemData.getItemName() : imodel.getItemName());
			itemService.update(itemData);
        	}else {
        		return "No such Item with itemid"+imodel.getItemId();
        	}
        	}
        	
    }else {
    	return "No Data in req body";
    }
        return"items updated";
    }
    }
