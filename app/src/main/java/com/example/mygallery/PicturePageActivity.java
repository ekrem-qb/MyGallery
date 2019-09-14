package com.example.mygallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PicturePageActivity extends Activity {

    String URL;
    TextView pictureTitle;
    TextView pictureURL;
    ImageView picture;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_page);

        Intent intent = getIntent();
        URL = intent.getStringExtra("URL_O");

        pictureURL = findViewById(R.id.pictureURL);
        pictureURL.setText(URL);

        pictureTitle = findViewById(R.id.pictureTitle);
        pictureTitle.setText(intent.getStringExtra("Title"));

        picture = findViewById(R.id.picture);
        progressBar = findViewById(R.id.pictureProgressBar);


        Picasso picasso = Picasso.get();
        picasso.load(URL).into(picture, new Callback() {
            @Override
            public void onSuccess() {
                ((ViewManager)progressBar.getParent()).removeView(progressBar);
            }

            @Override
            public void onError(Exception e) {
                pictureTitle.setTextColor(Color.rgb(255, 0, 0));
                pictureTitle.setText("Ошибка загрузки изображения!");
            }
        });

    }
}
