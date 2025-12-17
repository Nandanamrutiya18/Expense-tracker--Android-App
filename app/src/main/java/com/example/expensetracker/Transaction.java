package com.example.expensetracker;

public class Transaction {
    private int id;
    private String description;
    private double amount;
    private String type;
    private String dateTime;

    public Transaction(int id, String description, double amount, String type, String dateTime) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getDateTime() {
        return dateTime;
    }
}