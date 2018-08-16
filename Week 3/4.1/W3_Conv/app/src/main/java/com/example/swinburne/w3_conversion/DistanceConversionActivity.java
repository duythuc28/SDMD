package com.example.swinburne.w3_conversion;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swinburne.w3_conversion.Conversion.DistanceConversion;

public class DistanceConversionActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtFeet;
    EditText edtMiles;
    EditText edtInches;
    CheckBox chkboxDisplayMetres;
    TextView txtViewResult;

    private static String STATE_FEET_KEY = "FeetText";
    private static String STATE_MILES_KEY = "MileText";
    private static String STATE_INCHES_KEY = "InchesText";
    private static String STATE_RESULT_KEY = "Result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_conversion);
        initializeUI(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_FEET_KEY, edtFeet.getText().toString());
        outState.putString(STATE_MILES_KEY, edtMiles.getText().toString());
        outState.putString(STATE_INCHES_KEY, edtInches.getText().toString());
        outState.putString(STATE_RESULT_KEY, txtViewResult.getText().toString());
        super.onSaveInstanceState(outState);
    }

    /**
     * This method uses to restore state
     * @param state
     */
    private void restoreState(Bundle state) {
        if (state == null) return;
        edtFeet.setText(state.getString(STATE_FEET_KEY));
        edtInches.setText(state.getString(STATE_MILES_KEY));
        edtMiles.setText(state.getString(STATE_INCHES_KEY));
        txtViewResult.setText(state.getString(STATE_RESULT_KEY));
    }

    private void initializeUI(Bundle state) {
        edtInches = findViewById(R.id.edtInches);
        edtFeet = findViewById(R.id.edtFeet);
        edtMiles = findViewById(R.id.edtMiles);
        txtViewResult = findViewById(R.id.txtViewResult);
        chkboxDisplayMetres = findViewById(R.id.chkboxDisplayMetres);
        restoreState(state);
        Button btnConvertMiles = findViewById(R.id.btnConvert);
        btnConvertMiles.setOnClickListener(this);

    }

    /**
     * This method uses to check whether these edit text have value or not
     * @return
     */
    private boolean isValid() {
        return edtInches.getText().toString().length() > 0 && edtMiles.getText().toString().length() > 0 && edtFeet.getText().length() > 0;
    }

    @Override
    public void onClick(View view) {
        String result;
        String metres = getResources().getString(R.string.metres);
        if (isValid()) {
            DistanceConversion conversion = new DistanceConversion(Double.valueOf(edtInches.getText().toString()) ,Double.valueOf(edtFeet.getText().toString()), Double.valueOf(edtMiles.getText().toString()), chkboxDisplayMetres.isChecked());
            if (chkboxDisplayMetres.isChecked()) {
                result = String.format("%.2f %s", conversion.toValue(), metres);
            } else {
                result = String.format("%.2f cm", conversion.toValue());
            }

        } else {
            result = "";
            Toast.makeText(getApplicationContext(), "You have to enter a valid value", Toast.LENGTH_SHORT).show();
        }
        txtViewResult.setText(result);
    }

}
