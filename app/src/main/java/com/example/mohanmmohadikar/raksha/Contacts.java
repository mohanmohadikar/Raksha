package com.example.mohanmmohadikar.raksha;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class Contacts extends AppCompatActivity {

    DatabaseHelper myDb;

    private TextInputLayout l1,l2,l3,l4,l5;

    String n1,n2,n3,n4,n5;
    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        myDb = new DatabaseHelper(this);


        l1= (TextInputLayout)findViewById(R.id.l1);
        l2= (TextInputLayout)findViewById(R.id.l2);
        l3= (TextInputLayout)findViewById(R.id.l3);
        l4= (TextInputLayout)findViewById(R.id.l4);
        l5= (TextInputLayout)findViewById(R.id.l5);

        b1= (Button) findViewById(R.id.update);
        //kb2= (Button) findViewById(R.id.list);




        b1.setOnClickListener(v->{

            String id="1";
            boolean isUpdate = myDb.updateData(id,l1.getEditText().getText().toString(),
                    l2.getEditText().getText().toString(),
                    l3.getEditText().getText().toString(),
                    l4.getEditText().getText().toString(),
                    l5.getEditText().getText().toString()
            );
            if(isUpdate == true) {
                Toast.makeText(Contacts.this, "Data Update", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(Contacts.this, "Data not Updated", Toast.LENGTH_LONG).show();
            }

        });

    }
}
