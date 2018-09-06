package com.example.swinburne.foodparcel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ToggleButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FoodActivity extends AppCompatActivity {

    ImageView imgDetail;
    EditText edtImageName;
    EditText edtLocation;
    EditText edtKeywords;
    EditText edtDate;
    EditText edtEmail;
    RatingBar ratingBar;
    ToggleButton btnShare;
    Calendar myCalendar = Calendar.getInstance();
    Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);
        Bundle data = getIntent().getExtras();
        food = data.getParcelable("food");
        setupView(food);
    }

    private void setupView(Food food) {

        imgDetail = findViewById(R.id.imgDetail);
        edtImageName = findViewById(R.id.edtImageName);
        edtLocation = findViewById(R.id.edtLocationImage);
        edtKeywords = findViewById(R.id.edtKeywords);
        edtDate = findViewById(R.id.edtDate);
        edtEmail = findViewById(R.id.edtEmail);
        ratingBar = findViewById(R.id.ratingStar);
        btnShare = findViewById(R.id.btnShare);

        imgDetail.setImageResource(food.getImageRef());
        edtImageName.setText(food.getName());
        edtLocation.setText(food.getImageURL());
        edtKeywords.setText(food.getKeywords());
        edtEmail.setText(food.getOwners());
        btnShare.setChecked(food.isShareable());
        ratingBar.setRating(food.getRating());
        updateLabel(food.getDate());


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar.getTime());
            }

        };

        edtDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(FoodActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(Date date) {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtDate.setText(sdf.format(date));
    }

    public void clickEditButton (View v) {
        food.setName(edtImageName.getText().toString());
        food.setImageURL(edtLocation.getText().toString());
        food.setRating((int) ratingBar.getRating());
        food.setShareable(btnShare.isChecked());
        food.setKeywords(edtKeywords.getText().toString());
        food.setOwners(edtEmail.getText().toString());
        String dateString = edtDate.getText().toString();
        Date date= null;
        try {
            date = new SimpleDateFormat("dd/MM/yy").parse(dateString);
        } catch (ParseException e) {

        }
        food.setDate(date);

        Intent intent = new Intent();
        intent.putExtra("food_back", food);
        setResult(RESULT_OK, intent);
        finish();
    }
}
