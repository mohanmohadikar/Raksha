package com.example.mohanmmohadikar.raksha;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

        Intent i = getIntent();
        num1 = i.getStringExtra("k1");
        num2 = i.getStringExtra("k2");
        num3 = i.getStringExtra("k3");
        num4 = i.getStringExtra("k4");
        num5 = i.getStringExtra("k5");




        tv1 = (TextView)findViewById(R.id.tv1);
        b1 = (Button)findViewById(R.id.b1);
        b2 = (Button)findViewById(R.id.b2);
        cl = (Button)findViewById(R.id.cl);


        cl.setOnClickListener(v->{
            Intent in = new Intent(MainActivity.this, ListContact.class);

            //Intent i = new Intent(Contacts.this, ContactList.class);


            in.putExtra("key1", num1);
            in.putExtra("key2", num2);
            in.putExtra("key3", num3);
            in.putExtra("key4", num4);
            in.putExtra("key5", num5);

            startActivity(in);
        });


        b1.setOnClickListener(v->{

            Intent in = new Intent(this, Contacts.class);
            startActivity(in);


        });


        b2.setOnClickListener(v->{

            makeCall();

        });


        tv1.setOnClickListener(v -> {



            if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, MyPermission);
            }else{
                getLocation(v);
                LOCATE = LATTI + " " + LONGI;
                SmsManager sms = SmsManager.getDefault();

                if(num1!=null&&num1!="Contact number not updated"){
                    sms.sendTextMessage(num1, null, message , null, null);
                }
                if(num2!=null&&num2!="Contact number not updated"){
                    sms.sendTextMessage(num2, null, message , null, null);
                }
                if(num3!=null&&num3!="Contact number not updated"){
                    sms.sendTextMessage(num3, null, message , null, null);
                }
                if(num4!=null&&num4!="Contact number not updated"){
                    sms.sendTextMessage(num4, null, message , null, null);
                }
                if(num5!=null&&num5!="Contact number not updated"){
                    sms.sendTextMessage(num5, null, message , null, null);
                }







                Toast.makeText(MainActivity.this, "Success !", Toast.LENGTH_SHORT).show();
            }



        });
    }

    private void makeCall() {
        if(num1.trim().length()>2){
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






}
