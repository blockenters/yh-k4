package com.block.networkapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.block.networkapp2.adapter.PostAdapter;
import com.block.networkapp2.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PostAdapter adapter;
    ArrayList<Post> postArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "https://jsonplaceholder.typicode.com/posts",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        // 1. 네트워크로부터 데이터를 파싱한후 어레이리스트에 추가!

                        try {

                            for(int i = 0; i < response.length(); i++){
                                JSONObject object = response.getJSONObject(i);

                                int userId = object.getInt("userId");
                                int id = object.getInt("id");
                                String title = object.getString("title");
                                String body = object.getString("body");

                                Post post = new Post(userId, id, title, body);
                                postArrayList.add(post);
                            }


                        } catch (JSONException e) {
                            return;
                        }


                        // 2. 화면에 보여준다.
                        adapter = new PostAdapter(MainActivity.this, postArrayList);
                        recyclerView.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        queue.add(request);

    }
}





