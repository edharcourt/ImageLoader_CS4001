package com.example.ehar.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ehar on 10/4/2016.
 */

public class NetworkImageListViewAdapter
        extends ArrayAdapter<String> {

    Bitmap placeholder = null;
    String [] urls = null;

    public NetworkImageListViewAdapter(
        Context ctx, int resource, String [] urls, Bitmap placeholder) {
        super(ctx,resource,urls); // verify if need urls?
        this.placeholder = placeholder;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls.length;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return urls[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(
            int position,
            View reusedView,
            ViewGroup parent) {

        if (reusedView == null) {
            reusedView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.list_item, parent, false);
        }

        // get the textview in the list item
        TextView tv = (TextView) reusedView.findViewById(R.id.list_item_text);
        ImageView iv = (ImageView) reusedView.findViewById(R.id.list_item_image);

        tv.setText(getItem(position));

        iv.setImageBitmap(placeholder);

        return reusedView;
    }
}
