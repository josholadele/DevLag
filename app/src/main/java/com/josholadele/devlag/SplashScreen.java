package com.josholadele.devlag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

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

        String url = "https://jsonplaceholder.typicode.com/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Developer> developerList = new ArrayList<>();
                        for(int i = 0; i < response.length();i++){


                            Developer developer = new Developer();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                developer.setProfileUrl(jsonObject.optString("website"));
                                developer.setPhotoUrl("https://avatars1.githubusercontent.com/u/8110201?v=3");
                                developer.setUsername(jsonObject.optString("username"));
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
                        Toast.makeText(SplashScreen.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
        DevLagApplication.getInstance().addToRequestQueue(jsonArrayRequest);

    }
}
