package com.example.swinburne.browsing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    ImageButton imgBtn1;
    ImageButton imgBtn2;
    ImageButton imgBtn3;
    ImageButton imgBtn4;

    private static int COMPRESS_QUALITY = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
    }

    private void initializeUI() {
        imgBtn1 = findViewById(R.id.imgBtnFirst);
        imgBtn2 = findViewById(R.id.imgBtnSecond);
        imgBtn3 = findViewById(R.id.imgBtnThird);
        imgBtn4 = findViewById(R.id.imgBtnFourth);

    }

    public void clickFirstButton(View v) {
        try {
            Intent intent = new Intent(this, ImageDisplayActivity.class);
            byte[] byteArray = convertImageToBytes((BitmapDrawable) imgBtn1.getDrawable(), COMPRESS_QUALITY);
            intent.putExtra("image", byteArray);
            intent.putExtra("description", getString(R.string.image_description_1));
            startActivity(intent);
        } catch (IOException ex) {
            Log.e("Error", "Cannot convert image");
        }
    }

    public void clickSecondButton(View v) {
        try {
            Intent intent = new Intent(this, ImageDisplayActivity.class);
            byte[] byteArray = convertImageToBytes((BitmapDrawable) imgBtn2.getDrawable(), COMPRESS_QUALITY);
            intent.putExtra("image", byteArray);
            intent.putExtra("description", getString(R.string.image_description_2));
            startActivity(intent);
        } catch (IOException ex) {
            Log.e("Error", "Cannot convert image");
        }
    }

    public void clickThirdButton(View v) {
        try {
            Intent intent = new Intent(this, ImageDisplayActivity.class);
            byte[] byteArray = convertImageToBytes((BitmapDrawable) imgBtn3.getDrawable(), COMPRESS_QUALITY);
            intent.putExtra("image", byteArray);
            intent.putExtra("description", getString(R.string.image_description_3));
            startActivity(intent);
        } catch (IOException ex) {
            Log.e("Error", "Cannot convert image");
        }
    }

    public void clickFourthButton(View v) {
        try {
            Intent intent = new Intent(this, ImageDisplayActivity.class);
            byte[] byteArray = convertImageToBytes((BitmapDrawable) imgBtn4.getDrawable(), COMPRESS_QUALITY);
            intent.putExtra("image", byteArray);
            intent.putExtra("description", getString(R.string.image_description_4));
            startActivity(intent);
        } catch (IOException ex) {
            Log.e("Error", "Cannot convert image");
        }

    }

    /**
     * Convert image from drawable to byteArray[]
     * @param bitmapDrawable resource drawable image
     * @param quality   image quality after scaling
     * @return byteArray[] with quality
     * @throws IOException closing exception
     */
    private byte[] convertImageToBytes (BitmapDrawable bitmapDrawable, int quality) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality, stream);
        byte[] byteArray = stream.toByteArray();
        stream.close();
        return byteArray;
    }

}
