package com.example.ehar.imageloader;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

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

        ViewHolder vh = null;

        if (reusedView == null) {
            reusedView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.list_item, parent, false);
            // get the textview in the list item
            TextView tv = (TextView) reusedView.findViewById(R.id.list_item_text);
            ImageView iv = (ImageView) reusedView.findViewById(R.id.list_item_image);
            vh = new ViewHolder(iv,tv);
            reusedView.setTag(vh);
        }
        else {
            vh = (ViewHolder) reusedView.getTag();
        }


        //vh.tv.setText(getItem(position));  // TODO delete or not?
        //vh.iv.setImageBitmap(placeholder);

        // We don't always fire up a new task
        if (cancelPotentialWork(getItem(position), vh.iv)) {
            final DownloadBitmapTask task =
                    new DownloadBitmapTask(vh,getItem(position));
            final AsyncDrawable asyncDrawable = new AsyncDrawable(
                    getContext().getResources(), placeholder, task);
            vh.iv.setImageDrawable(asyncDrawable);
            task.execute();
        }

        return reusedView;
    }

    /**
     * ViewHolder
     */
    private static class ViewHolder {
        ImageView iv;
        TextView tv;
        ViewHolder(ImageView iv, TextView tv) {
            this.iv = iv;
            this.tv = tv;
        }
    }

    /**
     * AsyncTask for downloading an image from the network
     */
    class DownloadBitmapTask extends
            AsyncTask<Void, Void, Bitmap> {

        ViewHolder vh = null;
        String url = null;

        public DownloadBitmapTask(ViewHolder vh, String url) {
            this.vh = vh;
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(Void... junk) {

            // TODO don't hardcode dimensions
            return Utility.downloadBitmap(this.url, 120,120);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            if (isCancelled())
                bitmap = null;

            // TODO what should we do if image not loaded?
            // an error image placeholder?
            if (bitmap == null)
                return;

            if (this == getBitmapWorkerTask(vh.iv) &&
                    vh != null && vh.iv != null && this.url != null){
                vh.iv.setImageBitmap(bitmap);
                vh.tv.setText(this.url);
            }

        }
    }

    // Helper function that gets the reference to the async task
    // given an ImageView
    private static DownloadBitmapTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    public static boolean cancelPotentialWork(
            String url, ImageView imageView) {
        final DownloadBitmapTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final String bitmapUrl = bitmapWorkerTask.url;

            // If bitmapUrl is not set or it differs from the new url
            if (bitmapUrl == null || (!bitmapUrl.equals(url))) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<DownloadBitmapTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             DownloadBitmapTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                    new WeakReference<>(bitmapWorkerTask);
        }

        public DownloadBitmapTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

}
