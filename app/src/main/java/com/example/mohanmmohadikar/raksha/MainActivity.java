package com.example.mohanmmohadikar.raksha;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    private TextView tv1;
    private Button b2,b1, cl;

    private String message = "this is testing ";
    private String num = "9545319111";
    String num1,num2,num3,num4,num5;

    String LOCATE, LATTI, LONGI;
    double latti;
    double longi;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private GpsTracker gpsTracker;

    int MyPermission = 1;
    int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myDb = new DatabaseHelper(this);



        tv1 = (TextView) findViewById(R.id.tv1);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        cl = (Button) findViewById(R.id.cl);


        cl.setOnClickListener(v -> {


            Cursor res = myDb.getAllData();
            if (res.getCount() == 0) {
                // show message
                showMessage("Error", "Nothing found");
                return;
            }

            StringBuffer buffer = new StringBuffer();
            while (res.moveToNext()) {

                buffer.append("PERSON 1 :" + res.getString(1) + "\n\n");
                buffer.append("PERSON 2 :" + res.getString(2) + "\n\n");
                buffer.append("PERSON 3 :" + res.getString(3) + "\n\n");
                buffer.append("PERSON 4 :" + res.getString(4) + "\n\n");
                buffer.append("PERSON 5 :" + res.getString(5) + "\n\n");
            }

            // Show all data
            showMessage("CONTACTS", buffer.toString());


        });


        b1.setOnClickListener(v -> {

            Intent in = new Intent(this, Contacts.class);
            startActivity(in);


        });


        b2.setOnClickListener(v -> {

            makeCall();

        });


        Cursor c1 = myDb.getAllData();
        c1.moveToFirst();
        num1 = c1.getString(1);
        num2 = c1.getString(2);
        num3 = c1.getString(3);
        num4 = c1.getString(4);
        num5 = c1.getString(5);


        tv1.setOnClickListener(v -> {


            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, MyPermission);
            } else {
                getLocation(v);
                LOCATE = LATTI + " " + LONGI;
                SmsManager sms = SmsManager.getDefault();

                if (num1 != null && num1 != "Contact number not updated") {
                    sms.sendTextMessage(num1, null, message+" "+"Search my location on Google Maps: "+LOCATE, null, null);
                }
                if (num2 != null && num2 != "Contact number not updated") {
                    sms.sendTextMessage(num2, null, message+" "+"Search my location on Google Maps: "+LOCATE, null, null);
                }
                if (num3 != null && num3 != "Contact number not updated") {
                    sms.sendTextMessage(num3, null, message+" "+"Search my location on Google Maps: "+LOCATE, null, null);
                }
                if (num4 != null && num4 != "Contact number not updated") {
                    sms.sendTextMessage(num4, null, message+" "+"Search my location on Google Maps: "+LOCATE, null, null);
                }
                if (num5 != null && num5 != "Contact number not updated") {
                    sms.sendTextMessage(num5, null, message+" "+"Search my location on Google Maps: "+LOCATE, null, null);
                }


                Toast.makeText(MainActivity.this, "Success !", Toast.LENGTH_SHORT).show();
            }


        });


    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    private void makeCall() {
        if(num1.trim().length()>2){
            if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MyPermission);
            }else{
                String dial = "tel:"+num1;
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






}