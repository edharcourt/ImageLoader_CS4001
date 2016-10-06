package com.example.ehar.imageloader;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton main_image_view = null;

    Handler h = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_image_view = (ImageButton) findViewById(R.id.main_image);
        h = new Handler();

        main_image_view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                main_image_view.setVisibility(View.GONE);
            }
        });

        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        MainActivity.this,
                        LoadFromExternalStorageActivity.class
                );
                startActivity(intent);
            }
        });

        Button next_network = (Button) findViewById(R.id.network_images);
        next_network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        MainActivity.this,
                        LoadFromNetworkActivity.class
                );
                startActivity(intent);
            }
        });

    }

    void showMainImage(final Drawable d) {
        h.post(new Runnable() {
            @Override
            public void run() {
                main_image_view.setImageDrawable(d);
                main_image_view.setVisibility(View.VISIBLE);
            }
        });
    }

}
