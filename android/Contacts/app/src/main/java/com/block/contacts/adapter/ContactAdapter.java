package com.block.contacts.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.block.contacts.R;
import com.block.contacts.UpdateActivity;
import com.block.contacts.data.DatabaseHandler;
import com.block.contacts.model.Contact;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    Context context;
    ArrayList<Contact> contactArrayList;

    public ContactAdapter(Context context, ArrayList<Contact> contactArrayList) {
        this.context = context;
        this.contactArrayList = contactArrayList;
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row, parent, false);
        return new ContactAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Contact contact = contactArrayList.get(position);

        holder.txtName.setText(contact.name);
        holder.txtPhone.setText(contact.phone);

    }

    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtPhone;
        ImageView imgDelete;

        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            cardView = itemView.findViewById(R.id.cardView);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 알러트 다이얼로그 띄운다.
                    showAlertDialog();
                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // UpdateActivity 를 실행하는 코드를 여기에 작성

                    Intent intent = new Intent(context, UpdateActivity.class);
                    context.startActivity(intent);

                }
            });
        }

        private void showAlertDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("주소록 삭제");
            builder.setMessage("정말 삭제하시겠습니까?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    int index = getAdapterPosition();

                    // DB에서 삭제.
                    DatabaseHandler handler = new DatabaseHandler(context, "contact_db", null, 1);

                    // id 가 필요한데, Contact 가 묶음처리하니까
                    // 객체를 먼저 가져온다. 그 안에 들어있으니까!!!!!!!!!!!!!
                    Contact contact = contactArrayList.get(index);

                    handler.deleteContact(contact);

                    contactArrayList.remove(index);

                    notifyDataSetChanged();

                }
            });
            builder.setNegativeButton("NO", null);
            builder.show();
        }

    }

}
