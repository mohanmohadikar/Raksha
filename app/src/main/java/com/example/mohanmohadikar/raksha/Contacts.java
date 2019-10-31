package com.example.mohanmohadikar.raksha;

import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Contacts extends AppCompatActivity {


    DatabaseHelper myDb;

    private TextInputLayout l1,l2,l3,l4,l5;
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

/*

        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {

                            boolean isInserted = myDb.insertData(l1.getEditText().getText().toString(),
                                    l2.getEditText().getText().toString(),
                                    l3.getEditText().getText().toString(),
                                    l4.getEditText().getText().toString(),
                                    l5.getEditText().getText().toString());
                            if(isInserted == true)
                                Toast.makeText(Contacts.this,"CONTACTS UPDATED",Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(Contacts.this,"CONTACTS NOT UPDATED",Toast.LENGTH_LONG).show();
                        }
                        else{

                            boolean isUpdated = myDb.updateData("1",l1.getEditText().getText().toString(),
                                    l2.getEditText().getText().toString(),
                                    l3.getEditText().getText().toString(),
                                    l4.getEditText().getText().toString(),
                                    l5.getEditText().getText().toString());
                            if(isUpdated == true)
                                Toast.makeText(Contacts.this,"CONTACTS UPDATED",Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(Contacts.this,"CONTACTS NOT UPDATED",Toast.LENGTH_LONG).show();

                        }
                    }
                }
        );

        */
    }
}
