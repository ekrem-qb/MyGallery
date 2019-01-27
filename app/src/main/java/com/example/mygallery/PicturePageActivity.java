package com.example.mygallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;

public class PicturePageActivity extends Activity {

    LinearLayout linearLayout;
    String id;
    String title;
    TextView textView;
    ImageView picture;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_page);
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        title = intent.getStringExtra("Title");
        linearLayout = findViewById(R.id.pictureLayout);
        textView = findViewById(R.id.photo_title);
        textView.setText(title);
        picture = findViewById(R.id.photo);
        progressBar = findViewById(R.id.progressBar);

        new PicturePageActivity.LoadPicture().execute();
    }

    private class LoadPicture extends AsyncTask<Void, Void, List<PhotoItem>> {

        @Override
        protected List<PhotoItem> doInBackground(Void... voids) {
            return new FlickerGetSizes().getSizes(id);
        }

        @Override
        protected void onPostExecute(List<PhotoItem> items) {
            pastePicture(items);
        }
    }

    private List<PhotoItem> pastePicture(List<PhotoItem> photos) {
        Picasso picasso = Picasso.get();
        picasso.load(photos.get(photos.size() - 1).getUrl()).into(picture, new Callback() {
            @Override
            public void onSuccess() {
                ((ViewManager)progressBar.getParent()).removeView(progressBar);
            }

            @Override
            public void onError(Exception e) {
                textView.setTextColor(Color.rgb(255, 0, 0));
                textView.setText("Ошибка загрузки изображения!");
            }
        });

        return null;
    }
}
