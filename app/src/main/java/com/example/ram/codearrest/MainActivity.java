package com.example.ram.codearrest;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.service.media.CameraPrewarmService;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageButton capture,compile;

    public void capture(View view)
    {
        Intent cameraIntent = new Intent(MainActivity.this, PreviewClass.class);
        startActivity(cameraIntent);
    }

    public void gotoListView(View v)
    {
        Intent imageIntent = new Intent(MainActivity.this,ShowImages.class);
        startActivity(imageIntent);
    }

    public void writeCode(View view)
    {
        Intent editIntent = new Intent(MainActivity.this,Editable.class);
        startActivity(editIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        capture = (ImageButton) findViewById(R.id.capture);
        compile = (ImageButton) findViewById(R.id.compile);
    }

}