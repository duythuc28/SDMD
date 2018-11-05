package com.example.swinburne.w2_conversion.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swinburne.w2_conversion.model.Conversion;
import com.example.swinburne.w2_conversion.R;
import com.example.swinburne.w2_conversion.presenter.ConvertPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ConvertView {

    EditText edtFeet;
    EditText edtMiles;
    EditText edtInches;
    CheckBox chkboxDisplayMetres;
    TextView txtViewResult;
    ConvertPresenter presenter = new ConvertPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
        presenter.onCreate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
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
        if (isValid()) {
            presenter.onConvertButtonClick(Double.valueOf(edtInches.getText().toString()) ,Double.valueOf(edtFeet.getText().toString()), Double.valueOf(edtMiles.getText().toString()), chkboxDisplayMetres.isChecked());
        }
    }


    @Override
    public void displayResult(String displayLabel) {
        txtViewResult.setText(displayLabel);
    }
}
