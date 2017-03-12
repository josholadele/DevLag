package com.josholadele.devlag;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {

    String TAG = SplashScreen.class.getSimpleName();
    public static final Map<String, Developer> DEVELOPER_MAP = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getDeveloperData();
    }

    private void getDeveloperData() {

        String url = "https://api.github.com/search/users?q=location:lagos+language:java&per_page=100";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<Developer> developerList = new ArrayList<>();
                        JSONArray items = response.optJSONArray("items");

                        for(int i = 0; i < items.length();i++){


                            Developer developer = new Developer();
                            try {
                                JSONObject jsonObject = items.getJSONObject(i);
                                developer.setProfileUrl(jsonObject.optString("html_url"));
                                developer.setPhotoUrl(jsonObject.optString("avatar_url"));
                                developer.setUsername(jsonObject.optString("login"));
                                developerList.add(developer);
                                DEVELOPER_MAP.put(developer.getUsername(), developer);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        Intent intent = new Intent(SplashScreen.this, DeveloperListActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putParcelableArrayListExtra("DeveloperList",developerList);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        new AlertDialog.Builder(SplashScreen.this)
                                .setTitle("Failed to retrieve")
                                .setMessage("Unable to fetch data at this time")
                                .setCancelable(false)
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        getDeveloperData();
                                    }
                                })
                                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                    }
                                })
                                .create().show();
                        Toast.makeText(SplashScreen.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
        DevLagApplication.getInstance().addToRequestQueue(jsonObjectRequest);

    }
}
