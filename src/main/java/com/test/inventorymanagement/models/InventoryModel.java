package com.test.inventorymanagement.models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "inventory")
public class InventoryModel {
    private Long itemId;
    private Integer count;
    private Date updatedOn;
    private ItemModel itemByItemId;
    

	@Id
    @Column(name = "item_id", nullable = false)
   public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "count", nullable = true)
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "updated_on", nullable = false)
    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    /*@Override
	public
    boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryModel that = (InventoryModel) o;
        if (itemId != null ? !itemId.equals(that.itemId) : that.itemId != null) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        if (updatedOn != null ? !updatedOn.equals(that.updatedOn) : that.updatedOn != null) return false;
        return true;
    }
    @Override
	public
    int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (updatedOn != null ? updatedOn.hashCode() : 0);
        return result;
    }*/

    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "item_id", nullable = false)
    @JsonIgnore
    public ItemModel getItemByItemId() {
        return itemByItemId;
    }

    public void setItemByItemId(ItemModel itemByItemId) {
        this.itemByItemId = itemByItemId;
    }
}