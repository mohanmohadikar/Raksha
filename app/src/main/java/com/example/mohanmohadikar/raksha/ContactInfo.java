package com.example.mohanmohadikar.raksha;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactInfo extends AppCompatActivity {


    private Button add;
    DatabaseHelper myDb;

    private static final int PICK_CONTACT = 100;
    private static final int MyPermission = 1;

    String name = "";
    String number = "";
    private List<MyListData> list = new ArrayList<>();

    public String filename = "log.txt";
    String num1,num2,num3,num4,num5;
    MyListData listData;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        myDb = new DatabaseHelper(this);



        try {
            getContactInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(MyListData j:list){
            System.out.println("list started");
            System.out.println(j);
            System.out.println("list ended");
        }

        add = (Button)findViewById(R.id.add);

        add.setOnClickListener(v->{


            if (ContextCompat.checkSelfPermission(ContactInfo.this, Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ContactInfo.this, new String[]{Manifest.permission.READ_CONTACTS}, MyPermission);
            }

            if (ContextCompat.checkSelfPermission(ContactInfo.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ContactInfo.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MyPermission);
            }

            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);


            for(MyListData j:list){
                System.out.println("list started");
                System.out.println(j);
                System.out.println("list ended");
            }



        });


        System.out.println(name+" || "+number);

        MyListData listData = new MyListData(name,number);
        list.add(listData);



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
               // String name = "";
               // String number = "";
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



                System.out.println(name+"mai mc"+number);
                b(name, number);

                System.out.println(name+" || "+number);

                cursor.close();
            }
        }
    }



    private void getContactInfo(){

        try{
            Cursor res = myDb.getAllData();
            int size = res.getCount();

            res.moveToFirst();



            listData = new MyListData("djcsjcsj", "bsjsjsj j");
            list.add(listData);
            for(int i=0;i<size;i++){

                name = res.getString(1);
                number = res.getString(2);
                listData = new MyListData(name, number);
                list.add(listData);
                res.moveToNext();

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        listData = new MyListData("getinf", ":::bsjsjsj j");
        list.add(listData);
/*
        Cursor res = myDb.getAllData();
        int id = res.getCount();
        if (res.getCount() == 0) {

            listData = new MyListData("nnnnnnnndjcsjcsj", ":::bsjsjsj j");
            list.add(listData);
            // show message
            Toast.makeText(this,"Error! Nothing found", Toast.LENGTH_LONG).show();
            return;
        }
        else{

            res.moveToFirst();



            listData = new MyListData("djcsjcsj", "bsjsjsj j");
            list.add(listData);
            for(int i=0;i<id;i++){

                name = res.getString(1);
                number = res.getString(2);
                listData = new MyListData(name, number);
                list.add(listData);
                res.moveToNext();

            }



            //num1 = res.getString(1);
            //num2 = res.getString(2);
           // num3 = res.getString(3);
            //num4 = res.getString(4);
            //num5 = res.getString(5);
        }


*/
    }




    private void b(String name, String number) {

        int id=0;

        try{
            Cursor res = myDb.getAllData();
            id = res.getCount();

            if (id == 0) {

                boolean isInserted = myDb.insertData(id++, name, number);
                if (isInserted == true)
                    Toast.makeText(ContactInfo.this, "CONTACTS UPDATED", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(ContactInfo.this, "CONTACTS NOT UPDATED", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            id = 0;
        }


        if (id != 0) {

            boolean isUpdated = myDb.updateData(String.valueOf(id++), name, number);
            if (isUpdated == true)
                Toast.makeText(ContactInfo.this, "CONTACTS UPDATED", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(ContactInfo.this, "CONTACTS NOT UPDATED", Toast.LENGTH_LONG).show();
        }


    }

}
