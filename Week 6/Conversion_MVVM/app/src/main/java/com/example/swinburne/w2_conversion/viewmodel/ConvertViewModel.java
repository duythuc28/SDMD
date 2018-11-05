package com.example.swinburne.w2_conversion.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.util.Log;

import com.example.swinburne.w2_conversion.model.Conversion;

public class ConvertViewModel extends BaseObservable implements IViewModel {

    private Conversion model;

    public final ObservableField<String> convertResult = new ObservableField<>();

    public ConvertViewModel() {
        model = new Conversion();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    public void onConvertButtonClick() {
        String result;
        if (model.isMetres()) {
            result = String.format("%.2f meters", model.toValue());
        } else {
            result = String.format("%.2f cm", model.toValue());
        }
        convertResult.set(result);
    }


    @Bindable
    public String getInch() {
        return String.valueOf(model.getInch());
    }

    public void setInch(String inch) {
        if (!inch.isEmpty()) {
            if (Double.valueOf(inch) != model.getInch()) {
                model.setInch(Double.valueOf(inch));
                notifyChange();
            }
        }
    }

    @Bindable
    public String getFeet() {
        return String.valueOf(model.getFeet());
    }

    public void setFeet(String feet) {
        // Avoids infinite loops.
        if (!feet.isEmpty()) {
            if (Double.valueOf(feet) != model.getFeet()) {
                model.setFeet(Double.valueOf(feet));
                notifyChange();
            }
        }
    }

    @Bindable
    public String getMile() {
        return String.valueOf(model.getMile());
    }

    public void setMile(String mile) {
        if (!mile.isEmpty()) {
            if (Double.valueOf(mile) != model.getMile()) {
                model.setMile(Double.valueOf(mile));
                notifyChange();
            }
        }
    }

    @Bindable
    public Boolean getMeters() {
        return model.isMetres();
    }

    public void setMeters(Boolean meters) {
        if (model.isMetres() != meters) {
            model.setMetres(meters);
            notifyChange();
        }
    }
}
