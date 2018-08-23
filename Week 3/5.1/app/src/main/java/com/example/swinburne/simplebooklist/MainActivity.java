package com.example.swinburne.simplebooklist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.swinburne.simplebooklist.Adapter.BookAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Book> bookList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
    }

    private void initData() {
        bookList.add(new Book("Java programming", R.drawable.java, 1));
        bookList.add(new Book("C programming", R.drawable.cplus, 2));
        bookList.add(new Book("C# programming", R.drawable.csharp, 3));
        bookList.add(new Book("Go programming", R.drawable.go, 4));
        bookList.add(new Book("Python programming", R.drawable.python,5));
        bookList.add(new Book("Better business", R.drawable.betterbusiness, 1));
        bookList.add(new Book("Business bible", R.drawable.bookbible, 2));
        bookList.add(new Book("Meditation handbook", R.drawable.handbook, 3));
        bookList.add(new Book("Meditation", R.drawable.meditation, 4));
        bookList.add(new Book("How to start a business", R.drawable.savemoney,5));
        mAdapter.notifyDataSetChanged();
    }

    private void initializeUI() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new BookAdapter(bookList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        initData();
    }
}
