package com.example.swinburne.w2_conversion.presenter;

public interface IPresenter {
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();
    public void onConvertButtonClick(double inch, double feet, double mile, boolean isMetres);
}
