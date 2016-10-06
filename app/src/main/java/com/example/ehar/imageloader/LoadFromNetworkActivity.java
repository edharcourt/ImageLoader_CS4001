package com.example.ehar.imageloader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LoadFromNetworkActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_from_network);

        ListView lv = (ListView) findViewById(R.id.listview);


        Bitmap placeholder =
            BitmapFactory
                .decodeResource(getResources(),
                        R.drawable.placeholder);

        ArrayAdapter<String> adapter =
            new NetworkImageListViewAdapter(
                    this, R.layout.list_item,
                    Images.imageUrls,
                    placeholder);

        lv.setAdapter(adapter);
    }
}
