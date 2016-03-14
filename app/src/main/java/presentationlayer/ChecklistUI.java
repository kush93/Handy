package presentationlayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.view.View.OnClickListener;



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

import android.net.Uri;

import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

/**
import com.example.TodoList.db.TaskContract;
import com.example.TodoList.db.TaskDBHelper;
 **/




import com.example.kushal.rihabhbhandari.R;



/**
 * Created by Abdul Hadi on 12/03/2016.
 */
public class ChecklistUI extends Activity
{

    Button button;
    final Context context = this;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        addListenerOnButton();
    }


    public void addListenerOnButton() {

        button = (Button) findViewById(R.id.button_add_task);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Add Task");

                final EditText inputField = new EditText(context);
                // set dialog message
                alertDialogBuilder
                        .setMessage("Please enter the new task")
                        .setView(inputField)
                        .setCancelable(false)
                        .setPositiveButton("Save",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Log.d("ChecklistActivity",inputField.getText().toString());
                                // if this button is clicked, close
                                // current activity
                                //ChecklistUI.this.finish();
                            }
                        })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();





            }

        });

    }









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
                        Log.d("MainActivity",inputField.getText().toString());
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




