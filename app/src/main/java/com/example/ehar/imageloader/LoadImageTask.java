package com.example.ehar.imageloader;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by ehar on 9/29/2016.
 */

public class LoadImageTask extends AsyncTask<String,Void,Bitmap> {

    int w, h;
    ImageView v = null;

    public LoadImageTask(ImageView v) {
        this.w = v.getWidth();
        this.h = v.getHeight();
        this.v = v;
    }

    @Override
    protected Bitmap doInBackground(String... path) {
        return Utility.decodeSampledBitmap(path[0], w, h);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        v.setImageBitmap(bitmap);
    }
}
