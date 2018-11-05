package com.example.swinburne.w2_conversion.presenter;

import com.example.swinburne.w2_conversion.model.Conversion;
import com.example.swinburne.w2_conversion.view.ConvertView;

public class ConvertPresenter implements IPresenter {

    private ConvertView view;
    private Conversion model;

    public ConvertPresenter(ConvertView view) {
        this.view = view;
        this.model = new Conversion();
    }

    @Override
    public void onCreate() {
        model = new Conversion();
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

    public void onConvertButtonClick(double inch, double feet, double mile, boolean isMetres) {
        model.setData(inch, feet, mile, isMetres);
//        double result = model.toValue();
        String result;
        if (isMetres) {
            result = String.format("%.2f meters", model.toValue());
        } else {
            result = String.format("%.2f cm", model.toValue());
        }
        view.displayResult(String.valueOf(result));
    }
}
