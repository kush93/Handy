package persistancelayer;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rishabhbhandari on 2016-03-11.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Notes.db";
    public static final String TABLE_NAME = "note_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Time";
    public static final String COL_3 = "Name";
    public static final String COL_4 = "Label";
    public static final String COL_5 = "TextNote";
    public static final String COL_6 = "FilePath";
    public static final String COL_7 = "NoteType";


    Context showmsg;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        showmsg=context;
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,TIME TEXT,NAME TEXT,LABEL TEXT,TEXTNOTE TEXT, FILEPATH TEXT, NOTETYPE TEXT)");//executes whatever query is passed method as an arg
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String time, String name, String label, String textNote, String filePath, String noteType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, time);
        contentValues.put(COL_3, name);
        contentValues.put(COL_4, label);
        contentValues.put(COL_5, textNote);
        contentValues.put(COL_6, filePath);
        contentValues.put(COL_7, noteType);


        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;


    }
    public List<String> getData(String noteType) {
        List<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;//+"WHERE " + noteType

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount()==0)
        {
            showMessage("Error", "Nothing found");
            //return;
        }

        String data=new String() ;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {


                data.concat(cursor.getString(0)+" ");   //id
                data.concat(cursor.getString(1)+" ");   //time
                data.concat(cursor.getString(2)+" ");   //name
                data.concat(cursor.getString(3)+" ");   //label
                data.concat(cursor.getString(4)+" ");   //textNote
                data.concat(cursor.getString(5)+" ");   //filePath
                data.concat(cursor.getString(6)+" ");   //noteType

                // Adding contact to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        // return contact list
        return dataList;
    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(showmsg);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
//    public Cursor getAllData() // cursor = This interface provides random read-write access to the result set returned by a database query.
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
//        return res;
//
//
//    }
//
//
//    public String[] getData(String noteType) {
//
//
//        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + "WHERE " + noteType, null);
//        cursor.moveToFirst();
//        ArrayList<String> noteData = new ArrayList<String>();
//        while (!cursor.isAfterLast()) {
//            noteData.add(cursor.getString(cursor.getColumnIndex("name")));
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return noteData.toArray(new String[noteData.size()]);
//    }

}
