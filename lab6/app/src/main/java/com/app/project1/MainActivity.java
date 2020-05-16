package com.app.project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static String Link = "https://swarm.cs.pub.ro/~adriana/smd/android-kotlin.png";
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;

    public Button download;
    public ImageView Image;
    Spinner spinner;
    String text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        download = findViewById(R.id.download);
        spinner = findViewById(R.id.spinner);
        Image = findViewById(R.id.image);
        download.setOnClickListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.button,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
        if (spinner.getSelectedItem().toString().trim().equals("External")) {
            text = "External";
            Toast.makeText(MainActivity.this, "op" + text, Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download:
                text = String.valueOf(spinner.getSelectedItem());

                if (text.contains("External")) {

                    Picasso.get().load(Link).into(Image);
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "No Permission", Toast.LENGTH_SHORT).show();
                    } else {
                        MyAsyncTask asyncTask = new MyAsyncTask();
                        asyncTask.execute();
                    }

                }
                    if (text.contains("Internal") || text.contains("Undefined")) {

                        Picasso.get().load(Link).into(Image);
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "No Permission", Toast.LENGTH_SHORT).show();
                        } else {
                            MYTask asyncTask = new MYTask();
                            asyncTask.execute();
                        }

                    }
                }
        }



        @SuppressLint("StaticFieldLeak")
        class MyAsyncTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL url = new URL(Link);
                    //create the new connection
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    //set up some things on the connection
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoOutput(true);
                    //and connect!
                    urlConnection.connect();
                    //set the path where we want to save the file in this case, going to save it on the root directory of the sd card.
                    File SDCardRoot = Environment.getExternalStorageDirectory();
                    //create a new file, specifying the path, and the filename which we want to save the file as.
                    File file = new File(SDCardRoot, "image.jpg");
                    //this will be used to write the downloaded data into the file we created
                    FileOutputStream fileOutput = new FileOutputStream(file);
                    //this will be used in reading the data from the internet
                    InputStream inputStream = urlConnection.getInputStream();
                    //this is the total size of the file
                    int totalSize = urlConnection.getContentLength();
                    //variable to store total downloaded bytes
                    int downloadedSize = 0;
                    byte[] buffer = new byte[1024];
                    int bufferLength = 0; //used to store a temporary size of the buffer
                    //now, read through the input buffer and write the contents to the file
                    while ((bufferLength = inputStream.read(buffer)) > 0) {
                        //add the data in the buffer to the file in the file output stream (the file on the sd card
                        fileOutput.write(buffer, 0, bufferLength);
                        //add up the size so we know how much is downloaded
                        downloadedSize += bufferLength;
                        //this is where you would do something to report the prgress, like this maybe
                        //updateProgress(downloadedSize, totalSize);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Image Downloaded to sd card", Toast.LENGTH_SHORT).show();
                Bitmap bm = BitmapFactory.decodeFile("/storage/emulated/0/image.jpg");
                Image.setImageBitmap(bm);
            }
        }


        @SuppressLint("StaticFieldLeak")
        class MYTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL url = new URL(Link);
                    //create the new connection
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    //set up some things on the connection
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoOutput(true);
                    //and connect!
                    urlConnection.connect();
                    //set the path where we want to save the file in this case, going to save it on the root directory of the sd card.
                    File SDCardRoot = Environment.getRootDirectory();
                    //create a new file, specifying the path, and the filename which we want to save the file as.
                    File file = new File(SDCardRoot, "image.jpg");
                    //this will be used to write the downloaded data into the file we created
                    FileOutputStream fileOutput = new FileOutputStream(file);
                    //this will be used in reading the data from the internet
                    InputStream inputStream = urlConnection.getInputStream();
                    //this is the total size of the file
                    int totalSize = urlConnection.getContentLength();
                    //variable to store total downloaded bytes
                    int downloadedSize = 0;
                    byte[] buffer = new byte[1024];
                    int bufferLength = 0; //used to store a temporary size of the buffer
                    //now, read through the input buffer and write the contents to the file
                    while ((bufferLength = inputStream.read(buffer)) > 0) {
                        //add the data in the buffer to the file in the file output stream (the file on the sd card
                        fileOutput.write(buffer, 0, bufferLength);
                        //add up the size so we know how much is downloaded
                        downloadedSize += bufferLength;
                        //this is where you would do something to report the prgress, like this maybe
                        //updateProgress(downloadedSize, totalSize);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Image Downloaded to sd card", Toast.LENGTH_SHORT).show();
                Bitmap bm = BitmapFactory.decodeFile("/storage/emulated/0/image.jpg");
                Image.setImageBitmap(bm);
            }
        }
    }







