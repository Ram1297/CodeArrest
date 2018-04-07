package com.example.ram.codearrest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
/**
 * Created by Ram on 3/12/2018.
 */

public class ForwardImage extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    Button convertOrCompile;
    String path;
    Boolean convert = true;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forward_image);

        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        file = new File(path);
        Log.i("Path" ,path);
        imageView = (ImageView)findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        imageView.setImageBitmap(decodeSampledBitmapFromFile(file.getAbsolutePath()));
        convertOrCompile = (Button) findViewById(R.id.convertOrCompile);
        convertOrCompile.setText("Convert");
    }

    public static Bitmap decodeSampledBitmapFromFile(String path)
    {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapFactory.decodeFile(path, options);
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(path,options);
    }

    public void  getTextFromImage(View view)
    {
        if (convert == true)
        {
            Bitmap bitmap = BitmapFactory.decodeFile(file.toString());

            TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

            if (!textRecognizer.isOperational())
            {
                Toast.makeText(getApplicationContext(),"Could not get text",Toast.LENGTH_LONG).show();
                return;
            }

            Frame frame = new Frame.Builder().setBitmap(bitmap).build();

            SparseArray<TextBlock> items = textRecognizer.detect(frame);

            StringBuilder stringBuilder = new StringBuilder();

            for (int i=0;i<items.size();i++)
            {
                TextBlock myItem = items.valueAt(i);
                stringBuilder.append(myItem.getValue());
                stringBuilder.append("\n");
            }

            textView.setText(stringBuilder.toString());
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            convert = false;
            convertOrCompile.setText("Compile");
        }
        else
        {
            //Compile Code here
        }
    }

}
