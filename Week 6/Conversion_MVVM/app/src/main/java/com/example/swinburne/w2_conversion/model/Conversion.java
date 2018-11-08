package com.example.swinburne.w2_conversion.model;

/**
 * Created by iOSDev on 8/9/18.
 */

public class Conversion {
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
    public Conversion(double inch, double feet, double mile, boolean isMetres) {
        this.inch = inch;
        this.feet = feet;
        this.mile = mile;
        this.isMetres = isMetres;
    }

    public Conversion() {
    }

    public void setData(double inch, double feet, double mile, boolean isMetres) {
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

    public double getInch() {
        return inch;
    }

    public void setInch(double inch) {
        this.inch = inch;
    }

    public double getFeet() {
        return feet;
    }

    public void setFeet(double feet) {
        this.feet = feet;
    }

    public double getMile() {
        return mile;
    }

    public void setMile(double mile) {
        this.mile = mile;
    }

    public boolean isMetres() {
        return isMetres;
    }

    public void setMetres(boolean metres) {
        isMetres = metres;
    }
}


