package com.block.networkapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView txtUserId;
    TextView txtId;
    TextView txtTitle;
    TextView txtBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUserId = findViewById(R.id.txtUserId);
        txtId = findViewById(R.id.txtId);
        txtTitle = findViewById(R.id.txtTitle);
        txtBody = findViewById(R.id.txtBody);

        // 네트워크 통신을 위한 Volley 라이브러리 사용.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // Request 요청을 만들어야 한다.
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "https://jsonplaceholder.typicode.com/posts/11",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // 1. 데이터를 가져오는 작업.

                        try {
                            int userId = response.getInt("userId");
                            int id = response.getInt("id");
                            String title = response.getString("title");
                            String body = response.getString("body");

                            // 2. 화면에 보여주는 작업.
                            txtUserId.setText(userId+"");
                            txtId.setText(id+"");
                            txtTitle.setText(title);
                            txtBody.setText(body);

                        } catch (JSONException e) {
                            Log.i("Mainactivity", e.toString());

                            Toast.makeText(MainActivity.this,
                                    "네트워크 통신 중 문제가 발생했습니다.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        JsonArrayRequest request1 = new JsonArrayRequest(
                Request.Method.GET,
                "https://jsonplaceholder.typicode.com/posts",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            // Parsing

                            int userId = response.getJSONObject(0).getInt("userId");
                            int id = response.getJSONObject(0).getInt("id");
                            String title = response.getJSONObject(0).getString("title");
                            String body = response.getJSONObject(0).getString("body");

                            txtUserId.setText(userId+"");
                            txtId.setText(id+"");
                            txtTitle.setText(title);
                            txtBody.setText(body);


                        } catch (JSONException e) {
                            return;
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );


        queue.add(request1);

    }
}











