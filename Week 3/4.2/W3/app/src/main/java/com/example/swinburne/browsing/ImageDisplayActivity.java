package com.example.swinburne.browsing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ImageDisplayActivity extends AppCompatActivity {

    ImageView imgView;

    private static final String INTENT_IMAGE_KEY = "image";
    private static final String INTENT_DESCRIPTION_KEY = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        initialiseUI();
    }

    private void initialiseUI() {
        imgView = findViewById(R.id.imgView);
        imgView.setImageResource(getIntent().getIntExtra(INTENT_IMAGE_KEY,0));
        String description = getIntent().getStringExtra(INTENT_DESCRIPTION_KEY);
        TextView txtView = findViewById(R.id.txtDescription);
        txtView.setText(description);
    }
}
