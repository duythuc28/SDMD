package com.example.swinburne.w3_conversion.Conversion;

/**
 * Created by iOSDev on 8/10/18.
 */

public class DistanceConversion {
    private double inch;
    private double feet;
    private double mile;
    private boolean isMetres;
    /**
     * Constructor of conversion class
     * @param inch inch value
     * @param feet feet value
     * @param mile the value of mile
     * @param isMetres the indicator to show whether metres or cm
     */
    public DistanceConversion(double inch, double feet, double mile, boolean isMetres) {
        this.inch = inch;
        this.feet = feet;
        this.mile = mile;
        this.isMetres = isMetres;
    }

    /**
     * This method uses to convert the data from inch, mile, feet to cm or metres
     * @return
     */
    public double toValue() {
        double inch = this.inch + this.feet * 12 + this.mile * 5280 * 12;
        if (isMetres)  {
            return inch * 2.54/100;
        }
        return (inch * 2.54);
    }
}
