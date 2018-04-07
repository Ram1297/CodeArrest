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
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageButton capture;
    private File[] files;
    private String[] filesPaths;
    private String[] filesNames;
    ListView listView;

    public void capture(View view)
    {
        Intent cameraIntent = new Intent(MainActivity.this, PreviewClass.class);
        startActivity(cameraIntent);
    }

    public void viewable() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show();
        } else {
            File dirDownload = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + File.separator + "CodeArrest");
            if (dirDownload.isDirectory()) {
                files = dirDownload.listFiles();
                filesPaths = new String[files.length];
                filesNames = new String[files.length];

                for (int i = 0; i < files.length; i++) {
                    filesPaths[i] = files[i].getAbsolutePath();
                    filesNames[i] = files[i].getName();
                }
            }
        }
    }

    public void updateList() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filesNames);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),"Hello "+filesNames[position],Toast.LENGTH_SHORT).show();
                //openInGallery(filesNames[position]);

                Intent intent = new Intent(view.getContext(), ForwardImage.class);
                intent.putExtra("path", Environment.getExternalStorageDirectory() + File.separator + "DCIM" + File.separator + "CodeArrest" + File.separator + filesNames[position]);
                //Log.i("path",Environment.getExternalStorageDirectory()+File.separator+"DCIM" + File.separator + "CodeArrest" + File.separator + filesNames[position]);
                startActivity(intent);
            }
        });
    }
    public void removeElement()
    {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        capture = (ImageButton) findViewById(R.id.capture);
        listView = (ListView) findViewById(R.id.listView);
        viewable();
        updateList();

        listView.setLongClickable(true);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure to delete image");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File toBeDeleted = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + File.separator + "CodeArrest" + File.separator + filesNames[position]);
                        toBeDeleted.delete();
                        dialog.dismiss();

                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();

                return true;
            }
        });
    }
}
