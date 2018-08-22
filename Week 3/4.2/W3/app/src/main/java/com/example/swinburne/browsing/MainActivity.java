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

    private static final String INTENT_IMAGE_KEY = "image";
    private static final String INTENT_DESCRIPTION_KEY = "description";


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
        Intent intent = new Intent(this, ImageDisplayActivity.class);
        intent.putExtra(INTENT_DESCRIPTION_KEY, getString(R.string.image_description_1));
        intent.putExtra(INTENT_IMAGE_KEY, R.drawable.desert);
        startActivity(intent);
    }

    public void clickSecondButton(View v) {
        Intent intent = new Intent(this, ImageDisplayActivity.class);
        intent.putExtra(INTENT_DESCRIPTION_KEY, getString(R.string.image_description_2));
        intent.putExtra(INTENT_IMAGE_KEY, R.drawable.thai);
        startActivity(intent);
    }

    public void clickThirdButton(View v) {
        Intent intent = new Intent(this, ImageDisplayActivity.class);
        intent.putExtra(INTENT_DESCRIPTION_KEY, getString(R.string.image_description_3));
        intent.putExtra(INTENT_IMAGE_KEY, R.drawable.chinese);
        startActivity(intent);
    }

    public void clickFourthButton(View v) {
        Intent intent = new Intent(this, ImageDisplayActivity.class);
        intent.putExtra(INTENT_DESCRIPTION_KEY, getString(R.string.image_description_4));
        intent.putExtra(INTENT_IMAGE_KEY, R.drawable.italian);
        startActivity(intent);
//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://9news.com.au")));
    }
}
