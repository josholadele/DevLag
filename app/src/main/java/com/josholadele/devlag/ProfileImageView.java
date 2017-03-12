package com.josholadele.devlag;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ProfileImageView extends AppCompatActivity {

    public static final String PROFILE_IMAGE_URL = "Profile Image Url";
    public static final String PROFILE_USERNAME = "Profile Username";

    Toolbar toolbar;
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        profileImage = (ImageView) findViewById(R.id.profile_image);


        String username = getIntent().getStringExtra(PROFILE_USERNAME);
        String photoUrl = getIntent().getStringExtra(PROFILE_IMAGE_URL);


        setSupportActionBar(toolbar);
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(username);
        }


        Glide.with(this).load(photoUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .fallback(R.drawable.avatar_contact)
                .into(profileImage);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
