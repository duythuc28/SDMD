package com.example.swinburne.task10;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx = findViewById(R.id.status);
    }

    public void click(View view) {
        new LongOperation().execute("");
    }

    private class LongOperation extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            for (int i = 3; i >= 0; i--) {
                try {
                    Thread.sleep(1000);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Integer... values) {
            tx.setText(String.valueOf(values[0]));
        }
    }
}
