package com.ievlev.test_task.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {

    private boolean paid;
    private Date modificationDate;
    private String ownerUsername;
    private List<OrderedItemDto> orderedItemList;

    public OrderDto(boolean paid, Date modificationDate, String ownerUsername, List<OrderedItemDto> orderedItemList) {
        this.paid = paid;
        this.modificationDate = modificationDate;
        this.ownerUsername = ownerUsername;
        this.orderedItemList = orderedItemList;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public List<OrderedItemDto> getOrderedItemList() {
        return orderedItemList;
    }

    public void setOrderedItemList(List<OrderedItemDto> orderedItemList) {
        this.orderedItemList = orderedItemList;
    }
}
