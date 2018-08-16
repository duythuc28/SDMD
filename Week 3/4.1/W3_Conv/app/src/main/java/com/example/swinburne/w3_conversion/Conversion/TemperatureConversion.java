package com.example.swinburne.w3_conversion.Conversion;

/**
 * Created by iOSDev on 8/10/18.
 */

public class TemperatureConversion {
    private double celcius;

    public TemperatureConversion(double celcius) {
        this.celcius = celcius;
    }

    public double toFahrenheit() {
        return (celcius * 1.8) + 32;
    }
}
