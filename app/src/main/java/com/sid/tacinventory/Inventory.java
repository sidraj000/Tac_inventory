package com.sid.tacinventory;

import android.content.Intent;

public class Inventory {
    String item;
    Integer quantity;
    public Inventory()
    {

    }
    public Inventory(String item, Integer quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}
