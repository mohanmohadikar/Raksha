package com.example.mohanmohadikar.raksha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ContactInfo extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);


        MyListData[] myListData = new MyListData[] {
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),
                new MyListData("Email", "abc"),



        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
