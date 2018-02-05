/*
 * CMPUT 301. Assignment 1.
 * SubBook
 *
 * Copyright (c) 2018. Yu Kin Law. CMPUT 301. University of Alberta. All rights reserved.
 */

package com.yklaw_subbook;

import java.text.SimpleDateFormat;

/**
 * Data Validation
 * @author Yu Kin Law
 * @version 1.0
 * @see MainActivity
 */
public class ValidData {

    /**
     * Check name against the required format
     * name (textual, up to 20 characters)
     *
     * @param name Subscription Name
     * @return Boolean: Whether name is valid
     */
    public static boolean checkName(String name) {
        if (name.length() > 1 && name.length() <= 20) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check date against the required format
     * date started (presented yyyy-mm-dd format)
     *
     * @param date Subscription Date Started
     * @return Boolean: Whether date is valid
     */
    public static boolean checkDate(String date) {
        // If Date is empty, return false
        if (date.length() == 0) {
            return false;
        }

        //Taken https://stackoverflow.com/questions/226618/how-to-transform-a-time-value-into-yyyy-mm-dd-format-in-java
        //2018-02-04
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Check charge against the required format
     * monthly charge (non-negative currency value, in Canadian dollars)
     *
     * @param charge Subscription Monthly Charge
     * @return Boolean: Whether charge is valid
     */
    public static boolean checkCharge(String charge) {
        // If charge is empty, return false
        if (charge.length() == 0) {
            return false;
        }

        // Check if charge is a non-negative float
        try {
            Float chargefl = Float.parseFloat(charge);
            // If charge is negative, return false
            if (chargefl < 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check comment against the required format
     * comment (textual, up to 30 characters)
     *
     * @param comment Subscription Comment
     * @return Boolean: Whether comment is valid
     */
    public static boolean checkComment(String comment) {
        if (comment.length() > 30) {
            return false;
        } else {
            return true;
        }
    }
}
