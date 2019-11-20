package com.example.mohanmohadikar.raksha;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactInfo extends AppCompatActivity {

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.SEND_SMS,
    };


    private ImageView add;
    DatabaseHelper myDb;

    private static final int PICK_CONTACT = 100;
    private static final int MyPermission = 1;

    String name = "";
    String number = "";
    private List<MyListData> list = new ArrayList<>();

    MyListData listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);


        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }





        myDb = new DatabaseHelper(this);

        showContacts();


        add = (ImageView)findViewById(R.id.add);

        add.setOnClickListener(v->{


            if (ContextCompat.checkSelfPermission(ContactInfo.this, Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ContactInfo.this, new String[]{Manifest.permission.READ_CONTACTS}, MyPermission);
            }

            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);


        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();

                Cursor cursor = getContentResolver().query(contactData, null, null, null, null);
                cursor.moveToFirst();
                String hasPhone = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                System.out.println(contactId+" || "+hasPhone);

                if (hasPhone.equals("1")) {
                    Cursor phones = getContentResolver().query
                            (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                            + " = " + contactId, null, null);
                    while (phones.moveToNext()) {
                        name = phones.getString(phones.getColumnIndex
                                (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)).replaceAll("[-() ]", " ");
                        number = phones.getString(phones.getColumnIndex
                                (ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("[-() ]", "");
                    }
                    phones.close();
                    //Do something with number
                } else {
                    Toast.makeText(getApplicationContext(), "THIS CONTACT HAS NO PHONE NUMBER", Toast.LENGTH_LONG).show();
                }

                insert(name, number);
                cursor.close();
            }
        }
    }



    private void showContacts() {

        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            Toast.makeText(this,"ADD CONTACTS",Toast.LENGTH_LONG).show();
            return;
        }
        else{

            res.moveToFirst();

            name = res.getString(1);
            number = res.getString(2);
        }

        do {
            name = res.getString(1);
            number = res.getString(2);
            listData = new MyListData(name, number);
            list.add(listData);
        }while (res.moveToNext());
    }




    private void insert(String name, String number) {


        Cursor res = myDb.getAllData();
        int id = res.getCount();

        boolean isInserted = myDb.insertData(name, number);
        if (isInserted == true){
            listData = new MyListData(name, number);
            list.add(listData);
            Toast.makeText(ContactInfo.this, "CONTACTS UPDATED", Toast.LENGTH_LONG).show();
        }

        else Toast.makeText(ContactInfo.this, "CONTACTS NOT UPDATED", Toast.LENGTH_LONG).show();

    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}
