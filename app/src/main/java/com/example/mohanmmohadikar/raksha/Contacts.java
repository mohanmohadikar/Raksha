package com.example.mohanmmohadikar.raksha;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class Contacts extends AppCompatActivity {

    private TextInputLayout l1,l2,l3,l4,l5;

    String n1,n2,n3,n4,n5;
     private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);


        l1= (TextInputLayout)findViewById(R.id.l1);
        l2= (TextInputLayout)findViewById(R.id.l2);
        l3= (TextInputLayout)findViewById(R.id.l3);
        l4= (TextInputLayout)findViewById(R.id.l4);
        l5= (TextInputLayout)findViewById(R.id.l5);

        b1= (Button) findViewById(R.id.update);
        //kb2= (Button) findViewById(R.id.list);




        b1.setOnClickListener(v->{


            n1 = l1.getEditText().getText().toString().trim();
            n2 = l2.getEditText().getText().toString().trim();
            n3 = l3.getEditText().getText().toString().trim();
            n4 = l4.getEditText().getText().toString().trim();
            n5 = l5.getEditText().getText().toString().trim();


            Intent i = new Intent(Contacts.this, ListContact.class);


            i.putExtra("key1", n1);
            i.putExtra("key2", n2);
            i.putExtra("key3", n3);
            i.putExtra("key4", n4);
            i.putExtra("key5", n5);
            startActivity(i);
            finish();
        });






    }
}
