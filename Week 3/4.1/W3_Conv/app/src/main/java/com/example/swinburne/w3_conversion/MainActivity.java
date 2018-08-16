package com.example.swinburne.w3_conversion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.swinburne.w3_conversion.Adapter.ConvertAdapter;

public class MainActivity extends AppCompatActivity {

    String[] convertName = {"Convert Temperature", "Convert Distance"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConvertAdapter convertAdapter = new ConvertAdapter(this, convertName);
        ListView listView = findViewById(R.id.conversion_list);
        listView.setAdapter(convertAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), convertName[i],Toast.LENGTH_SHORT).show();
                if (i == 0) {
                    // Starting temperature conversion activity
                    Intent intent = new Intent(getApplicationContext(), TemperatureConversionActivity.class);
                    startActivity(intent);
                } else {
                    // Starting Distance Conversion activity
                    Intent intent = new Intent(getApplicationContext(), DistanceConversionActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
