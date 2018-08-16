package com.example.swinburne.w3_conversion;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swinburne.w3_conversion.Conversion.TemperatureConversion;

public class TemperatureConversionActivity extends AppCompatActivity {

    EditText edtCelcius;
    TextView txtFahrenheit;

    private static String RESTORE_CELSIUS_KEY = "CelsiusText";
    private static String RESULT_KEY = "ResultText";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_conversion);
        initializeUI(savedInstanceState);
    }

    /**
     * This method uses to restore the state
     * @param state
     */
    private void restoretState(Bundle state) {
        if (state == null) return;
        edtCelcius.setText(state.getString(RESTORE_CELSIUS_KEY));
        txtFahrenheit.setText(state.getString(RESULT_KEY));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(RESTORE_CELSIUS_KEY, edtCelcius.getText().toString());
        outState.putString(RESULT_KEY, txtFahrenheit.getText().toString());
        super.onSaveInstanceState(outState);
    }

    private boolean isValid() {
        return edtCelcius.getText().toString().length() > 0;
    }

    private void initializeUI(Bundle state) {
        edtCelcius = findViewById(R.id.edtCelcius);
        txtFahrenheit = findViewById(R.id.txtFahrenheit);
        Button convertButton = findViewById(R.id.btnConvertTemp);
        restoretState(state);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    TemperatureConversion temperatureConversion = new TemperatureConversion(Double.valueOf(edtCelcius.getText().toString()));
                    double result = temperatureConversion.toFahrenheit();
                    txtFahrenheit.setText(String.format("%.2f °F", result));
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter valid number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
