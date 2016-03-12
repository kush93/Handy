package presentationlayer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kushal.rihabhbhandari.R;






import android.app.Activity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;


import android.content.Intent;

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


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

import businesslayer.TextNoteBL;
import com.example.kushal.rihabhbhandari.R;

import java.util.ArrayList;
import java.util.Arrays;



/**
 import com.example.TodoList.db.TaskContract;
 import com.example.TodoList.db.TaskDBHelper;
 */

import com.example.kushal.rihabhbhandari.R;

/**
 * Created by Abdul Hadi on 12/03/2016.
 */
public class ChecklistUI extends Activity {

    private Button add, newNote;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
    }





/**
    newNote = (Button) findViewById(R.id.button_add_task);
    newNote.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ChecklistUI.class);
            startActivity(intent);


**/

/**
            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.checklist_menu,menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.action_add_task:
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Add a task");
                        builder.setMessage("What do you want to do?");
                        final EditText inputField = new EditText(this);
                        builder.setView(inputField);
                        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("Checklist", inputField.getText().toString());
                            }
                        });

                        builder.setNegativeButton("Cancel",null);

                        builder.create().show();
                        return true;


                    default:
                        return false;
                }
            }

**/
        }
