package com.example.swinburne.w2_conversion.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.swinburne.w2_conversion.R;
import com.example.swinburne.w2_conversion.databinding.ActivityMainBinding;
import com.example.swinburne.w2_conversion.viewmodel.ConvertViewModel;

public class MainActivity extends AppCompatActivity {

    ConvertViewModel viewModel = new ConvertViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(viewModel);
        viewModel.onCreate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}
