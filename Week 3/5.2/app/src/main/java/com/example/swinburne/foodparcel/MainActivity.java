package com.example.swinburne.foodparcel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageButton imgBtn1;
    ImageButton imgBtn2;
    ImageButton imgBtn3;
    ImageButton imgBtn4;

    TextView txtCuisine1;
    TextView txtCuisine2;
    TextView txtCuisine3;
    TextView txtCuisine4;

    TextView txtDate1;
    TextView txtDate2;
    TextView txtDate3;
    TextView txtDate4;

    Food food1;
    Food food2;
    Food food3;
    Food food4;

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

        txtCuisine1 = findViewById(R.id.txtCuisineName1);
        txtCuisine2 = findViewById(R.id.txtCuisineName2);
        txtCuisine3 = findViewById(R.id.txtCuisineName3);
        txtCuisine4 = findViewById(R.id.txtCuisineName4);

        txtDate1 = findViewById(R.id.txtDate1);
        txtDate2 = findViewById(R.id.txtDate2);
        txtDate3 = findViewById(R.id.txtDate3);
        txtDate4 = findViewById(R.id.txtDate4);

        food1 = new Food("Desert", "https://www.google.com.au", R.drawable.desert, "desert", new Date(), 2, "abc@gmail.com");
        food2 = new Food("Thai Cuisine", "https://www.google.com.au", R.drawable.thai,"thai" ,new Date(), 2, "abc@gmail.com");
        food3 = new Food("Chinese Cuisine", "https://www.google.com.au", R.drawable.chinese,"chinese", new Date(), 2, "abc@gmail.com");
        food4 = new Food("Italian Cuisine", "https://www.google.com.au", R.drawable.italian, "italian",new Date(), 2, "abc@gmail.com");
        setupView1(food1);
        setupView2(food2);
        setupView3(food3);
        setupView4(food4);
    }

    private void setupView1(Food food) {
        imgBtn1.setImageResource(food.getImageRef());
        txtCuisine1.setText("Name: " + food.getName());
//        txtCuisine1.setText(food.getDate());
        updateLabel(txtDate1, food.getDate());
    }

    private void setupView2(Food food) {
        imgBtn2.setImageResource(food.getImageRef());
        txtCuisine2.setText("Name: " + food.getName());
        updateLabel(txtDate2, food.getDate());
    }

    private void setupView3(Food food) {
        imgBtn3.setImageResource(food.getImageRef());
        txtCuisine3.setText("Name: " + food.getName());
        updateLabel(txtDate3, food.getDate());
    }
    private void setupView4(Food food) {
        imgBtn4.setImageResource(food.getImageRef());
        txtCuisine4.setText("Name: " + food.getName());
        updateLabel(txtDate4, food.getDate());
    }

    public void clickFirstButton(View v) {
        Intent intent = new Intent(this, FoodActivity.class);
        intent.putExtra("food", food1 );
        startActivityForResult(intent, 1);
    }

    public void clickSecondButton(View v) {
        Intent intent = new Intent(this, FoodActivity.class);
        intent.putExtra("food", food2 );
        startActivityForResult(intent, 2);
    }

    public void clickThirdButton(View v) {
        Intent intent = new Intent(this, FoodActivity.class);
        intent.putExtra("food", food3);
        startActivityForResult(intent, 3);
    }

    public void clickFourthButton(View v) {
        Intent intent = new Intent(this, FoodActivity.class);
        intent.putExtra("food", food4 );
        startActivityForResult(intent, 4);
    }

    private void updateLabel(TextView txtDate,Date date) {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtDate.setText(sdf.format(date));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            Food food = data.getExtras().getParcelable("food_back");
            switch (requestCode) {
                case 1:
                    food1 = food;
                    setupView1(food);
                    break;
                case 2:
                    food2 = food;
                    setupView2(food);
                    break;
                case 3:
                    food3 = food;
                    setupView3(food);
                    break;
                case 4:
                    food4 = food;
                    setupView4(food);
                    break;
                default:
                    break;
            }
        }

    }


}
