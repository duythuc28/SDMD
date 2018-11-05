package com.example.swinburne.w2_conversion.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swinburne.w2_conversion.R;
import com.example.swinburne.w2_conversion.model.Conversion;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Establish references with the view layout
    EditText edtFeet;
    EditText edtMiles;
    EditText edtInches;
    CheckBox chkboxDisplayMetres;
    TextView txtViewResult;

    private Conversion conversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
        // Instantiate the model
        conversion = new Conversion();
    }

    private void initializeUI() {
        edtInches = findViewById(R.id.edtInches);
        edtFeet = findViewById(R.id.edtFeet);
        edtMiles = findViewById(R.id.edtMiles);
        txtViewResult = findViewById(R.id.txtViewResult);
        chkboxDisplayMetres = findViewById(R.id.chkboxDisplayMetres);

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
            conversion.setData(Double.valueOf(edtInches.getText().toString()) ,Double.valueOf(edtFeet.getText().toString()), Double.valueOf(edtMiles.getText().toString()), chkboxDisplayMetres.isChecked());
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
