package com.Obook;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.util.ArrayList;

public class testBook {
    public int accountId;
    public String category;
    public boolean conditionUsed;
    public double discount;
    public String name;
    public double price;
    public int shipmentPlans;
    public int weight;
    public int id;

    public testBook(int accountId, String category, boolean conditionUsed, double discount, String name, double price, int shipmentPlans, int weight, int id) {
        this.accountId = accountId;
        this.category = category;
        this.conditionUsed = conditionUsed;
        this.discount = discount;
        this.name = name;
        this.price = price;
        this.shipmentPlans = shipmentPlans;
        this.weight = weight;
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getCategory() {
        return category;
    }

    public boolean isConditionUsed() {
        return conditionUsed;
    }

    public double getDiscount() {
        return discount;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getShipmentPlans() {
        return shipmentPlans;
    }

    public int getWeight() {
        return weight;
    }

    public int getId() {
        return id;
    }
}
