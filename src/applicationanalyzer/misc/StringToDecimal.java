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
public class StringToDecimal extends StringConverter<Number> {

    final String numberType;

    public StringToDecimal() {
        this("Double");
    }

    public StringToDecimal(String numberType) {
        this.numberType = numberType;
    }

    @Override
    public Number fromString(String value) {
        try {
            // If the specified value is null or zero-length, return null
            if (value == null) {
                return null;
            }

            value = value.trim();

            if (value.length() < 1) {
                return null;
            }
            if (this.numberType.equals("Double")) {
                return Double.parseDouble(value);
            } else {
                return Integer.parseInt(value);
            }
        } catch (NumberFormatException nfe) {
            return 0.0;
        }
    }

    @Override
    public String toString(Number value) {
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
