package com.block.contacts.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.block.contacts.model.Contact;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 테이블 생성문을 작성.

       String query = "create table contact ( id integer primary key autoincrement, name text, phone text ); ";
       sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 기존의 테이블을 삭제하고, 새 테이블 만드는 코드 작성.
    }

    // 필요한 CRUD 관련된 메소드들을 만들어 준다.
    public void addContact(Contact contact){

        SQLiteDatabase db = getWritableDatabase();

        String query = "insert into contact " +
                        " (name, phone)" +
                        " values " +
                        " ( ? ,  ? ); ";

        String[] record = { contact.name,  contact.phone };

        db.execSQL(query, record);

        db.close();
    }

    // 저장된 연락처를 모두 가져오는 메소드.
    public ArrayList<Contact>  getAllContacts(){

        SQLiteDatabase db = getWritableDatabase();

        String query = "select * from contact";

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Contact> contactArrayList = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {

                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);

                Contact contact = new Contact(id, name, phone);

                contactArrayList.add(contact);

            } while (cursor.moveToNext());
        }

        return contactArrayList;
    }


    public void deleteContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();

        String query = "delete from contact " +
                        "where id = ?";
        String[] record = { contact.id+"" };

        db.execSQL(query, record);

        db.close();

    }

}
