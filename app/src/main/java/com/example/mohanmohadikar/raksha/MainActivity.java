package com.example.mohanmohadikar.raksha;

import android.Manifest;
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
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    MediaPlayer myaudio;


    private Button alert;


    private String message = "THIS IS TESTING ";
    private String num = "9545319111";
    String num1;

    String LOCATE, LATTI, LONGI;
    double latti;
    double longi;

    private GpsTracker gpsTracker;

    int MyPermission = 1, PICK_CONTACT  = 100;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView mImageview, stop,scream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigationview);
        alert = (Button) findViewById(R.id.alert);
        stop = (ImageView) findViewById(R.id.stop);
        scream = (ImageView) findViewById(R.id.scream);
        mImageview = (ImageView) findViewById(R.id.imageView);
        myaudio = MediaPlayer.create(MainActivity.this, R.raw.anushree);



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


                    case R.id.contactmenu:
                        item.setChecked(true);

                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, MyPermission);
                        }

                        Intent i4 = new Intent(MainActivity.this, ContactInfo.class);
                        startActivity(i4);
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
    }
}


