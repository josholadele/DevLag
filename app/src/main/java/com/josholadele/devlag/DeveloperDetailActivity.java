package com.josholadele.devlag;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * An activity representing a single Developer detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link DeveloperListActivity}.
 */
public class DeveloperDetailActivity extends AppCompatActivity {

    LinearLayout profileItemsLayout;
    CircleImageView profileImage;
    Developer developer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profileItemsLayout = (LinearLayout) findViewById(R.id.layout_profile_items);
        profileImage = (CircleImageView) findViewById(R.id.profileImage);

        developer = getIntent().getParcelableExtra(DeveloperDetailFragment.ARG_ITEM_ID);

        Glide.with(this).load(developer.getPhotoUrl())/**/
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .fallback(R.drawable.avatar_contact)
                .into(profileImage);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(developer.getUsername());
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            addProfileItems(profileItemsLayout,developer);
//            Bundle arguments = new Bundle();
//            arguments.putString(DeveloperDetailFragment.ARG_ITEM_ID,
//                    getIntent().getStringExtra(DeveloperDetailFragment.ARG_ITEM_ID));
//
//            DeveloperDetailFragment fragment = new DeveloperDetailFragment();
//            fragment.setArguments(arguments);
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.developer_detail_container, fragment)
//                    .commit();
        }
    }

    void addProfileItems(LinearLayout linearLayout, Developer developer){

        View profileView = getLayoutInflater().inflate(R.layout.item_profile, linearLayout, false);
        ((TextView) profileView.findViewById(R.id.label_name)).setText("Username");
        ((TextView) profileView.findViewById(R.id.label_value)).setText(developer.getUsername());
        linearLayout.addView(profileView);
        linearLayout.addView(getLayoutInflater().inflate(R.layout.item_divider, linearLayout, false));

        View profileView1 = getLayoutInflater().inflate(R.layout.item_profile, linearLayout, false);
        ((TextView) profileView1.findViewById(R.id.label_name)).setText(R.string.profile_url_title);
        TextView profileTextView = (TextView) profileView1.findViewById(R.id.label_value);
        profileTextView.setText(developer.getProfileUrl());
        profileTextView.setTextColor(getResources().getColor(R.color.linkBlue));
        linearLayout.addView(profileView1);

    }

    private void shareApp(){

        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String text = getResources().getString(R.string.share_text,developer.getUsername());
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,R.string.app_name);
        Uri uri = Uri.parse(developer.getProfileUrl());
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,text+"\n"+uri.toString());
        startActivity(Intent.createChooser(shareIntent , "Share Using"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if(id == R.id.action_share){
            shareApp();
        }
        return super.onOptionsItemSelected(item);
    }
}
