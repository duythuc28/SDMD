package au.edu.swin.sdmd.suncalculatorjava;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import java.util.TimeZone;

import au.edu.swin.sdmd.suncalculatorjava.calc.GeoLocation;

public class AddGeoLocation extends AppCompatActivity {

    EditText edtLocationName;
    EditText edtLatitude;
    EditText edtLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_geo_location);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setupUI();
    }

    private void setupUI() {
        edtLocationName = findViewById(R.id.edtLocation);
        edtLatitude = findViewById(R.id.edtLatitude);
        edtLongitude = findViewById(R.id.edtLongitude);
    }

    public void saveButtonClick(View view) {
        // TODO: Save data to file and send a call back to previous activity finish
        if (!edtLongitude.getText().toString().equals("") &&
                !edtLatitude.getText().toString().equals("") &&
                !edtLocationName.getText().toString().equals("")) {
            GeoLocation geoLocation = new GeoLocation(edtLocationName.getText().toString(),
                    Float.valueOf(edtLatitude.getText().toString()),  Float.valueOf(edtLongitude.getText().toString()), TimeZone.getTimeZone("GMT"));
            LocationFile.appendInput(this, geoLocation);
//            Intent intent = new Intent();
//            intent.putExtra("geoLocation", food);
            setResult(RESULT_OK);
            this.finish();
        } else {
            Toast.makeText(this, "Please input value", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
