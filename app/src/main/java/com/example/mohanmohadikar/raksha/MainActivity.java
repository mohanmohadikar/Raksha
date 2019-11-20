package com.example.mohanmohadikar.raksha;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {




    private ImageView add;
  //  DatabaseHelper myDb;

    //private static final int PICK_CONTACT = 100;
    private static final int MyPermission2 = 12;

    String name = "";
    String number = "";
    private List<MyListData> list = new ArrayList<>();

    MyListData listData;








    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {

            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.SEND_SMS,
    };

    DatabaseHelper myDb;

    MediaPlayer myaudio;


    private ImageView alert;


    private String message = "I'm in danger. ";
    private String num = "9545319111";
    String num1;

    String LOCATE, LATTI, LONGI;
    double latti;
    double longi;

    private GpsTracker gpsTracker;

    int MyPermission = 1, PICK_CONTACT  = 100, PICK_CONTACT2  = 200;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView mImageview, stop,scream,send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }






        myDb = new DatabaseHelper(this);

        alert = (ImageView) findViewById(R.id.alert);
        stop = (ImageView) findViewById(R.id.stop);
        scream = (ImageView) findViewById(R.id.scream);
        send = (ImageView) findViewById(R.id.send);
        myaudio = MediaPlayer.create(MainActivity.this, R.raw.anushree);




        showContacts();

        scream.setOnClickListener(v->{


            myaudio.setLooping(true);
            myaudio.start();
            scream.setVisibility(v.INVISIBLE);
            stop.setVisibility(v.VISIBLE);
        });

        stop.setOnClickListener(v->{


            myaudio.pause();
            stop.setVisibility(v.INVISIBLE);
            scream.setVisibility(v.VISIBLE);

        });

        alert.setOnClickListener(v -> {


            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, MyPermission);
            } else {
                getLocation(v);
                LOCATE = LATTI + "," + LONGI;
                SmsManager sms = SmsManager.getDefault();


                String[] nums = getContacts();

                if(nums.length==0){
                    Intent i = new Intent(MainActivity.this, ContactInfo.class);
                    startActivity(i);
                    Toast.makeText(MainActivity.this, "UPDATE YOUR CONTACTLIST ", Toast.LENGTH_LONG).show();
                }
                else {

                    for(String str:nums){
                        sms.sendTextMessage(str, null, message+" "+"Search my location on Google Maps: "+ "http://maps.google.com/maps?q="+LOCATE, null, null);
                    }

                    Toast.makeText(MainActivity.this, "MESSAGE SENT SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    makeCall();

                }
            }
        });
        










        add = (ImageView)findViewById(R.id.add);

        add.setOnClickListener(v->{


            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, MyPermission);
            }

            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT2);
        });


        send.setOnClickListener(v->{
            shareButton();
        });




        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    private String[] getContacts() {

        Cursor res = myDb.getAllData();
        int size = res.getCount();

        int i=0;
        String[] Numbers = new String[size];
        String number;

        if (res.getCount() == 0) {
            Toast.makeText(this,"ADD CONTACTS",Toast.LENGTH_LONG).show();
        }
        else{

            res.moveToFirst();
            do {
                number = res.getString(2);
                Numbers[i++] = number;

            }while (res.moveToNext());
        }
        return Numbers;
    }




    private void makeCall() {
        if(num.trim().length()>2){
            if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MyPermission);
            }else{
                String dial = "tel:"+num;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }else{
            Toast.makeText(MainActivity.this, "Enter Number ", Toast.LENGTH_SHORT).show();
        }
    }


    public void getLocation(View view) {
        gpsTracker = new GpsTracker(MainActivity.this);
        if (gpsTracker.canGetLocation()) {
            latti = gpsTracker.getLatitude();
            longi = gpsTracker.getLongitude();
            LATTI = String.valueOf(latti);
            LONGI = String.valueOf(longi);
        } else {
            gpsTracker.showSettingsAlert();
        }
    }


    public String getLocationShare() {
        gpsTracker = new GpsTracker(MainActivity.this);
        if (gpsTracker.canGetLocation()) {
            latti = gpsTracker.getLatitude();
            longi = gpsTracker.getLongitude();
            LATTI = String.valueOf(latti);
            LONGI = String.valueOf(longi);
        } else {
            gpsTracker.showSettingsAlert();
        }

        return LATTI + "," + LONGI;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                String number = "";
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
                        number = phones.getString(phones.getColumnIndex
                                (ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("[-() ]", "");
                    }
                    phones.close();
                    //Do something with number
                } else {
                    Toast.makeText(getApplicationContext(), "This contact has no phone number", Toast.LENGTH_LONG).show();
                }

                cursor.close();
            }
        }


























        if (requestCode == PICK_CONTACT2) {
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
            refresh();
        }











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
            Toast.makeText(MainActivity.this, "CONTACTS UPDATED", Toast.LENGTH_LONG).show();
        }

        else Toast.makeText(MainActivity.this, "CONTACTS NOT UPDATED", Toast.LENGTH_LONG).show();

    }




    public void refresh(){
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        this.finish();
    }



    public void shareButton(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareSub = message+" "+"Search my location on Google Maps: "+ "http://maps.google.com/maps?q="+getLocationShare();
        intent.putExtra(intent.EXTRA_TEXT, shareSub);
        startActivity(Intent.createChooser(intent, "Share using"));


    }


}


