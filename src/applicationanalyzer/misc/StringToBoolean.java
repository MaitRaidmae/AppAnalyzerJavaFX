/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.misc;

import javafx.util.StringConverter;

/**
 *
 * @author Hundisilm Converts a number string to Decimal
 */
public class StringToBoolean extends StringConverter<Boolean> {

    public StringToBoolean() {
    }

    @Override
    public Boolean fromString(String value) {
        try {
            // If the specified value is null or zero-length, return null
            if (value == null) {
                return null;
            }

            value = value.trim();

            if (value.length() < 1) {
                return null;
            }
            
            //I know, I know, this could be a one-liner...
            if (value.equals("1")) {
                return true;
            } else {
                return false;
            }
            
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    @Override
    public String toString(Boolean value) {
        // If the specified value is null, return a zero-length String
        if (value == null) {
            return "";
        }

        // Perform the requested formatting
        try {
            return value.toString();
        } catch (NumberFormatException nfe) {
            return "";
        }
    }
}
