package com.example.swinburne.browsing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {


    ImageButton imgBtn1;
    ImageButton imgBtn2;
    ImageButton imgBtn3;
    ImageButton imgBtn4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
    }

    private void initializeUI() {
        imgBtn1 = findViewById(R.id.imgBtnFirst);
        imgBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFirstButton();
            }
        });

        imgBtn2 = findViewById(R.id.imgBtnSecond);
        imgBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSecondButton();
            }
        });

        imgBtn3 = findViewById(R.id.imgBtnThird);
        imgBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickThirdButton();
            }
        });

        imgBtn4 = findViewById(R.id.imgBtnFourth);
        imgBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFourthButton();
            }
        });
    }

    private void clickFirstButton() {
        Intent intent = new Intent(this, ImageDisplayActivity.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BitmapDrawable drawable = (BitmapDrawable) imgBtn1.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50, stream);
        byte[] byteArray = stream.toByteArray();
        intent.putExtra("image", byteArray);
        intent.putExtra("description", "This is the first image");
        startActivity(intent);
    }

    private void clickSecondButton() {
        Intent intent = new Intent(this, ImageDisplayActivity.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BitmapDrawable drawable = (BitmapDrawable) imgBtn2.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50, stream);
        byte[] byteArray = stream.toByteArray();
        intent.putExtra("image", byteArray);
        intent.putExtra("description", "This is the second image");
        startActivity(intent);
    }

    private void clickThirdButton() {
        Intent intent = new Intent(this, ImageDisplayActivity.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BitmapDrawable drawable = (BitmapDrawable) imgBtn3.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50, stream);
        byte[] byteArray = stream.toByteArray();
        intent.putExtra("image", byteArray);
        intent.putExtra("description", "This is the third image");
        startActivity(intent);
    }

    private void clickFourthButton() {
        Intent intent = new Intent(this, ImageDisplayActivity.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BitmapDrawable drawable = (BitmapDrawable) imgBtn4.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50, stream);
        byte[] byteArray = stream.toByteArray();
        intent.putExtra("image", byteArray);
        intent.putExtra("description", "This is the fourth image");
        startActivity(intent);
    }

}
