package com.akashdubey.demopickphoto;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Button launchGallery; //gallery launcher
    ImageView image;      // image placeholder
    Intent imgIntent=new Intent(Intent.ACTION_GET_CONTENT); // content intent
    public static final int PICK_STATUS=1;
    InputStream input;
    Uri selectedImage;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // the usual crap about creating object of various widgets

        launchGallery=(Button) findViewById(R.id.goGallery);
        image=(ImageView) findViewById(R.id.image);
        imgIntent.setType("image/*");

        launchGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(imgIntent.createChooser(imgIntent,"Select Pic.."),PICK_STATUS); // we use this to get call intent and get its result too

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);



    }


    //overriding activitResult to capture result code and proceed accordingly
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){


            try {
                // only if we are API 23 or above ask for runtime permission
                if( Build.VERSION.SDK_INT>=23){

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
                }

                    // store data to uri
                    selectedImage=data.getData();
                    input= getContentResolver().openInputStream(selectedImage); // pass and covert uri to inputstream
                    bitmap= BitmapFactory.decodeStream(input); // pass inputstream to bitmap
                    image.setImageBitmap(bitmap);               // place bitmap on imageview

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

}
