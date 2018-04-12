package com.example.ram.codearrest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Ram on 4/11/2018.
 */

public class ShowImages extends AppCompatActivity {


    private File[] files;
    private String[] filesPaths;
    private String[] filesNames;
    ListView listView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_images);
        listView = (ListView) findViewById(R.id.listView);

        viewable();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,filesNames);
        listView.setAdapter(adapter);

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

        listView.setLongClickable(true);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                AlertDialog.Builder alert = new AlertDialog.Builder(ShowImages.this);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure to delete image");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File toBeDeleted = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + File.separator + "CodeArrest" + File.separator + filesNames[position]);
                        toBeDeleted.delete();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(),"Image Deleted", Toast.LENGTH_SHORT);
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
