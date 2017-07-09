package com.naanizcustomer.naaniz.models;

/**
 * Created by hemba on 6/18/2017.
 */

public class Order {
    private String itemName, itemCategory, actionID, scheduledAt, vendorName, vendorLookupID;
    private boolean isDispatched, isCompleted, isAccepted, isConfirmed;

    public Order(String itemName, String itemCategory, String actionID, String scheduledAt, String vendorName, String vendorLookupID, boolean isDispatched, boolean isCompleted, boolean isAccepted, boolean isConfirmed) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.actionID = actionID;
        this.scheduledAt = scheduledAt;
        this.vendorName = vendorName;
        this.vendorLookupID = vendorLookupID;
        this.isDispatched = isDispatched;
        this.isCompleted = isCompleted;
        this.isAccepted = isAccepted;
        this.isConfirmed = isConfirmed;
    }

    public Order(String itemName, String itemCategory, String actionID, String scheduledAt, boolean isDispatched, boolean isCompleted, boolean isAccepted, boolean isConfirmed) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.actionID = actionID;
        this.scheduledAt = scheduledAt;
        this.isDispatched = isDispatched;
        this.isCompleted = isCompleted;
        this.isAccepted = isAccepted;
        this.isConfirmed = isConfirmed;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getActionID() {
        return actionID;
    }

    public void setActionID(String actionID) {
        this.actionID = actionID;
    }

    public String getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(String scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorLookupID() {
        return vendorLookupID;
    }

    public void setVendorLookupID(String vendorLookupID) {
        this.vendorLookupID = vendorLookupID;
    }

    public boolean isDispatched() {
        return isDispatched;
    }

    public void setDispatched(boolean dispatched) {
        isDispatched = dispatched;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
}
