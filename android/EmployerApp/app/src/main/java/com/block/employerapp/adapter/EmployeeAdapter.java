package com.block.employerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.block.employerapp.R;
import com.block.employerapp.model.Employee;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder>{

    Context context;
    ArrayList<Employee> employeeArrayList;

    public EmployeeAdapter(Context context, ArrayList<Employee> employeeArrayList) {
        this.context = context;
        this.employeeArrayList = employeeArrayList;
    }

    @NonNull
    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_row, parent, false);
        return new EmployeeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Employee employee = employeeArrayList.get(position);

        holder.txtName.setText(employee.name);
        holder.txtAge.setText("나이 : " + employee.age);

        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String srtSalary = decimalFormat.format(employee.salary);

        holder.txtSalary.setText("연봉 : $"+ srtSalary);

    }

    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtSalary;
        TextView txtAge;
        ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtSalary = itemView.findViewById(R.id.txtSalary);
            txtAge = itemView.findViewById(R.id.txtAge);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }

}
