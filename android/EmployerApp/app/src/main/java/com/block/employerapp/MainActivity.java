package com.block.employerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.block.employerapp.adapter.EmployeeAdapter;
import com.block.employerapp.model.Employee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;

    RecyclerView recyclerView;
    EmployeeAdapter adapter;
    ArrayList<Employee> employeeArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "https://block1-image-test.s3.ap-northeast-2.amazonaws.com/employees.json",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("EMPLOYER_APP", response.toString());
                        try {
                            JSONArray data = response.getJSONArray("data");

                            for(int i = 0; i < data.length(); i++){
                                JSONObject jsonObject = data.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("employee_name");
                                int salary = jsonObject.getInt("employee_salary");
                                int age = jsonObject.getInt("employee_age");

                                Employee employee = new Employee(id, name, salary, age);
                                employeeArrayList.add(employee);
                            }

                        } catch (JSONException e) {
                            Log.i("EMPLOYER_APP", e.toString());
                            return;
                        }

                        Log.i("EMPLOYER_APP", "이상없음");

                        adapter = new EmployeeAdapter(MainActivity.this, employeeArrayList);
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