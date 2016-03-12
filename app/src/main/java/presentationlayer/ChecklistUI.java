package presentationlayer;


import android.app.Activity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
/**
import com.example.TodoList.db.TaskContract;
import com.example.TodoList.db.TaskDBHelper;
 */

import com.example.kushal.rihabhbhandari.R;

/**
 * Created by Abdul Hadi on 11/03/2016.
 */




public class ChecklistUI extends Activity
{




    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checklist_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Log.d("MainActivity","Add a new task");
                return true;

            default:
                return false;
        }
    }

}
