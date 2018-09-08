package com.example.mohanmmohadikar.raksha;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tv1;
    private String message = "this is testing ";
    private String num1 = "9545319111";

    String LOCATE, LATTI,LONGI;
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

        tv1 = (TextView) findViewById(R.id.tv1);



        tv1.setOnClickListener(v -> {



                getLocation(v);
                LOCATE =LATTI+" "+LONGI;
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(num1,null,message + LOCATE,null,null);





            Toast.makeText(MainActivity.this, "Success !", Toast.LENGTH_SHORT).show();
        });
    }




    public void getLocation(View view){
        gpsTracker = new GpsTracker(MainActivity.this);
        if(gpsTracker.canGetLocation()){
            latti = gpsTracker.getLatitude();
            longi = gpsTracker.getLongitude();
            LATTI = String.valueOf(latti);
            LONGI = String.valueOf(longi);
        }else{
            gpsTracker.showSettingsAlert();
        }
    }




}
