package com.example.mohanmmohadikar.raksha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ContactList extends AppCompatActivity {

    TextView p1,p2,p3,p4,p5;
    String s1,s2,s3,s4,s5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        p1 = (TextView)findViewById(R.id.p1);
        p2 = (TextView)findViewById(R.id.p2);
        p3 = (TextView)findViewById(R.id.p3);
        p4 = (TextView)findViewById(R.id.p4);
        p5 = (TextView)findViewById(R.id.p5);




        Intent i = getIntent();
        s1 = i.getStringExtra("key1");
        s2 = i.getStringExtra("key2");
        s3 = i.getStringExtra("key3");
        s4 = i.getStringExtra("key4");
        s5 = i.getStringExtra("key5");



        Intent in = new Intent(ContactList.this, MainActivity.class);

        if(s1!=null){
            p1.setText(s1);
            in.putExtra("k1", s1);
        }
        else{
            p1.setText("Contact number not updated");
        }
        if(s2!=null){
            p2.setText(s2);
            in.putExtra("k2", s2);
        }
        else{
            p2.setText("Contact number not updated");
        }
        if(s3!=null){
            p3.setText(s3);
            in.putExtra("k3", s3);
        }
        else{
            p3.setText("Contact number not updated");
        }
        if(s4!=null){
            p4.setText(s4);
            in.putExtra("k4", s4);
        }
        else{
            p4.setText("Contact number not updated");
        }
        if(s5!=null){
            p5.setText(s5);
            in.putExtra("k5", s5);
        }
        else {
            p5.setText("Contact number not updated");
        }



    }
}
