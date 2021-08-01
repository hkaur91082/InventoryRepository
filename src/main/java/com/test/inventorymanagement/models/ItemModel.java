package com.test.inventorymanagement.models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "item")
public class ItemModel {
    private Long itemId;
    private String itemName;
    private String description;
    private Date addedOn;
    private InventoryModel inventoryByItemId;

  
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "item_id", nullable = false)
   public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "item_name", nullable = false, length = 256)
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 512)
   public String getDescription() {
        return description;
    }

   public void setDescription(String description) {
        this.description = description;
    }

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "added_on", nullable = false)
   public Date getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) 
        	return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemModel itemModel = (ItemModel) o;
        if (itemId != null ? !itemId.equals(itemModel.itemId) : itemModel.itemId != null) return false;
        if (itemName != null ? !itemName.equals(itemModel.itemName) : itemModel.itemName != null) return false;
        if (description != null ? !description.equals(itemModel.description) : itemModel.description != null)
            return false;
        if (addedOn != null ? !addedOn.equals(itemModel.addedOn) : itemModel.addedOn != null) return false;
        return true;
    }
    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (addedOn != null ? addedOn.hashCode() : 0);
        return result;
    }*/

   // @OneToOne(mappedBy = "itemByItemId")
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "itemByItemId")
   
    @JsonIgnore
    public InventoryModel getInventoryByItemId() {
        return inventoryByItemId;
    }

  public  void setInventoryByItemId(InventoryModel inventoryByItemId) {
        this.inventoryByItemId = inventoryByItemId;
    }

}
