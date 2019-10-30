package com.example.mohanmohadikar.raksha;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    MediaPlayer myaudio;


    private Button stop,scream, cl,alert;


    private String message = "THIS IS TESTING ";
    private String num = "9545319111";
    String num1,num2,num3,num4,num5;

    String LOCATE, LATTI, LONGI;
    double latti;
    double longi;

    private GpsTracker gpsTracker;

    int MyPermission = 1;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView mImageview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigationview);


        myDb = new DatabaseHelper(this);



        alert = (Button) findViewById(R.id.alert);
        stop = (Button) findViewById(R.id.stop);
        scream = (Button) findViewById(R.id.scream);
        mImageview = (ImageView) findViewById(R.id.imageView);


        myaudio = MediaPlayer.create(MainActivity.this, R.raw.anushree);


/*

        cl.setOnClickListener(v -> {


            Cursor res = myDb.getAllData();
            if (res.getCount() == 0) {
                // show message
                showMessage("Error", "Nothing found");
                return;
            }
            else{

                res.moveToFirst();

                num1 = res.getString(1);
                num2 = res.getString(2);
                num3 = res.getString(3);
                num4 = res.getString(4);
                num5 = res.getString(5);
            }

            StringBuffer buffer = new StringBuffer();
            do {

                buffer.append("PERSON 1 :" +"\n\t\t"+ res.getString(1) + "\n\n");
                buffer.append("PERSON 2 :" +"\n\t\t"+ res.getString(2) + "\n\n");
                buffer.append("PERSON 3 :" +"\n\t\t"+ res.getString(3) + "\n\n");
                buffer.append("PERSON 4 :" +"\n\t\t"+ res.getString(4) + "\n\n");
                buffer.append("PERSON 5 :" +"\n\t\t"+ res.getString(5) + "\n\n");
            }while (res.moveToNext());

            // Show all data
            showMessage("CONTACTS", buffer.toString());


        });*/








        scream.setOnClickListener(v->{


            myaudio.setLooping(true);
            myaudio.start();
            scream.setVisibility(v.INVISIBLE);
            stop.setVisibility(v.VISIBLE);
        });

        stop.setOnClickListener(v->{


            myaudio.stop();
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


                getContacts();

                if(isEmptyString(num1) && isEmptyString(num2) && isEmptyString(num3) && isEmptyString(num4) && isEmptyString(num5)){


                    Intent i = new Intent(MainActivity.this, Contacts.class);
                    startActivity(i);
                    Toast.makeText(MainActivity.this, "UPDATE YOUR CONTACTLIST ", Toast.LENGTH_LONG).show();
                }
                else {





                    if (num1 != null && num1 != "Contact number not updated") {
                        sms.sendTextMessage(num1, null, message+" "+"Search my location on Google Maps: "+ "http://maps.google.com/maps?q="+LOCATE, null, null);
                    }
                    if (num2 != null && num2 != "Contact number not updated") {
                        sms.sendTextMessage(num2, null, message+" "+"Search my location on Google Maps: "+ "http://maps.google.com/maps?q="+LOCATE, null, null);
                    }
                    if (num3 != null && num3 != "Contact number not updated") {
                        sms.sendTextMessage(num3, null, message+" "+"Search my location on Google Maps: "+ "http://maps.google.com/maps?q="+LOCATE, null, null);
                    }
                    if (num4 != null && num4 != "Contact number not updated") {
                        sms.sendTextMessage(num4, null, message+" "+"Search my location on Google Maps: "+ "http://maps.google.com/maps?q="+LOCATE, null, null);
                    }
                    if (num5 != null && num5 != "Contact number not updated") {
                        sms.sendTextMessage(num5, null, message+" "+"Search my location on Google Maps: "+ "http://maps.google.com/maps?q="+LOCATE, null, null);
                    }


                    Toast.makeText(MainActivity.this, "MESSAGE SENT SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    makeCall();

                }


            }



        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.use:
                        item.setChecked(true);
                        return true;

                    case R.id.stories:
                        item.setChecked(true);
                        Intent i2 = new Intent(MainActivity.this, Stories.class);
                        startActivity(i2);
                        return true;

                    case R.id.safety:
                        item.setChecked(true);
                        Intent i3 = new Intent(MainActivity.this, Safety.class);
                        startActivity(i3);
                        return true;

                    case R.id.register:
                        item.setChecked(true);
                        Intent i4 = new Intent(MainActivity.this, Contacts.class);
                        startActivity(i4);
                        return true;

                    case R.id.contactmenu:
                        item.setChecked(true);
                        contactmenu();
                        return true;

                }

                return false;
            }
        });

        mImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });


    }


    private void getContacts() {

        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            return;
        }
        else{

            res.moveToFirst();

            num1 = res.getString(1);
            num2 = res.getString(2);
            num3 = res.getString(3);
            num4 = res.getString(4);
            num5 = res.getString(5);
        }


    }



    private void contactmenu() {

        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            // show message
            showMessage("Error", "Nothing found");
            return;
        }
        else{

            res.moveToFirst();

            num1 = res.getString(1);
            num2 = res.getString(2);
            num3 = res.getString(3);
            num4 = res.getString(4);
            num5 = res.getString(5);
        }

        StringBuffer buffer = new StringBuffer();
        do {

            buffer.append("PERSON 1 :" +"\n\t\t"+ res.getString(1) + "\n\n");
            buffer.append("PERSON 2 :" +"\n\t\t"+ res.getString(2) + "\n\n");
            buffer.append("PERSON 3 :" +"\n\t\t"+ res.getString(3) + "\n\n");
            buffer.append("PERSON 4 :" +"\n\t\t"+ res.getString(4) + "\n\n");
            buffer.append("PERSON 5 :" +"\n\t\t"+ res.getString(5) + "\n\n");
        }while (res.moveToNext());

        // Show all data
        showMessage("CONTACTS", buffer.toString());

    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
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


    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }

    public void displayMessage(String Message){
        Toast.makeText(MainActivity.this,Message,Toast.LENGTH_LONG).show();
    }




}