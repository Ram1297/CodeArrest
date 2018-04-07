package com.example.ram.codearrest;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PreviewClass extends AppCompatActivity {

    ImageButton click,imports;
    FrameLayout frameLayout;
    Camera camera;
    ShowCam showCamera;
    MainActivity refreshing;


    public void takePicture(View view)
    {
        if (camera != null)
        {
            camera.takePicture(null,null,mPictureCallback);
        }
    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {
            if (data != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                if (bitmap != null) {

                    File file = new File(Environment.getExternalStorageDirectory() + File.separator + "DCIM" +File.separator+"CodeArrest");
                    if (!file.isDirectory()) {
                        file.mkdir();
                    }

                        camera.stopPreview();
                        file = new File(Environment.getExternalStorageDirectory() + File.separator + "DCIM" +File.separator + "CodeArrest", System.currentTimeMillis() + ".jpg");


                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

                            fileOutputStream.flush();
                            fileOutputStream.close();
                            refreshing.viewable();

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                }
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_class);

        click = (ImageButton) findViewById(R.id.click);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        imports = (ImageButton) findViewById(R.id.imp);

        //Open Camera
        camera = Camera.open();

        showCamera = new ShowCam(this,camera);

        frameLayout.addView(showCamera);




    }


}