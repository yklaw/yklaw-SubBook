/*
 * CMPUT 301. Assignment 1.
 * SubBook
 *
 * Copyright (c) 2018. Yu Kin Law. CMPUT 301. University of Alberta. All rights reserved.
 */

package com.yklaw_subbook;

/**
 * Represents a Subscription
 *
 * @author Yu Kin Law
 * @version 1.0
 */
public class Subscription {
    private String name;
    private String date;
    private String charge;
    private String comment;

    /**
     * Constructor for a subscription object
     * @param name Subscription Name
     * @param date Subscription Date Started
     * @param charge Subscription Monthly Charge
     * @param comment Subscription Comment
     */
    public Subscription(String name, String date, String charge, String comment) {
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comment = comment;
    }


    /**
     * Gets and Returns the Subscription Name
     * @return Subscription Name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets and Returns the Subscription Date Started
     * @return Subscription Date Started
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Gets and Returns the Subscription Monthly Charge
     * @return Subscription Monthly Charge
     */
    public String getCharge() {
        return this.charge;
    }

    /**
     * Gets and Returns the Subscription Comment
     * @return Subscription Comment
     */
    public String getComment() {
        return this.comment;
    }


    /**
     * Gets and Return all the infomation of a Subsrciption
     * @return String containing all 4 data
     */
    public String getSubInfo() {
        return "Name: " + this.name + "\nDate: " + this.date + "\nCharge: " + this.charge
                + "\nComment: " + this.comment;
    }
}
