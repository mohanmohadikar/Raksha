package com.example.mohanmmohadikar.raksha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListContact extends AppCompatActivity {

    private android.widget.ListView ListView;
    private MyAdapter adap;

    String s1,s2,s3,s4,s5;

   //private List<Data> clist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);

        Intent i = getIntent();
        s1 = i.getStringExtra("key1");
        s2 = i.getStringExtra("key2");
        s3 = i.getStringExtra("key3");
        s4 = i.getStringExtra("key4");
        s5 = i.getStringExtra("key5");



        ListView = (ListView)findViewById(R.id.recyclerView);
       // ListView.setHasFixedSize(true);
      //  ListView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Data> clist = new ArrayList<>();
        Data c1 = new Data(
                s1
        );

        clist.add(c1);

        Data c2 = new Data(
                s2
        );
        clist.add(c2);
        Data c3 = new Data(
                s3
        );
        clist.add(c3);
        Data c4 = new Data(
                s4
        );
        clist.add(c4);
        Data c5 = new Data(
                s5
        );
        clist.add(c5);


        adap = new MyAdapter(this,clist);
        ListView.setAdapter(adap);
    }

}
