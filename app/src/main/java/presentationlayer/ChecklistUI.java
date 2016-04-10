package presentationlayer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kushal.rihabhbhandari.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import businesslayer.ChecklistBL;

/**
 * Created by Abdul Hadi on 11/03/2016.
 */




public class ChecklistUI extends Activity
{

    ChecklistBL checkListBL = new ChecklistBL(this);


    Button button;
    private ArrayList<String> tasks;
    private ArrayAdapter<String> tasks_Adapter;
    private ListView lv_tasks;

    final Context context = this;

    TextView editName;
    Button saveCheckList;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);


        lv_tasks = (ListView) findViewById(R.id.list_task);
        tasks = new ArrayList<String>();
        tasks_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);
        lv_tasks.setAdapter(tasks_Adapter);
        editName = (TextView) findViewById(R.id.editText_name);
        saveCheckList=(Button) findViewById(R.id.button_save);
        add_Button_Listener();
        delete_task_Listener();

        saveCheckList.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String editedTime = DateFormat.getDateTimeInstance().format(new Date());
                // long representation of current time

                String checklistName = editName.getText().toString();

                if (editName.getText().toString().isEmpty()) {
                    Toast.makeText(ChecklistUI.this, "Title not specified", Toast.LENGTH_LONG).show();

                }
                else
                {
                    checkListBL.create(editedTime, checklistName, tasks, "CheckList");
                    Toast.makeText(ChecklistUI.this, "CheckList was saved", Toast.LENGTH_LONG).show();
                    // MainActivity.getInstance().dataAdded(editText_title);// not being used
                    finish();
                }


            }
        });

    }




   /* private void save_button_Listner()
    {
        if(editName.getText().toString().isEmpty()) {
            Toast.makeText(ChecklistUI.this, "Please enter name for the checkList", Toast.LENGTH_LONG).show();
        }
        else
        {

            Toast.makeText(ChecklistUI.this, "CheckList was saved", Toast.LENGTH_LONG).show();
        }


    }*/


    private void delete_task_Listener()
    {
        lv_tasks.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View task, int task_no, long id)
            {
                tasks.remove(task_no);                  //Deleting Task

                tasks_Adapter.notifyDataSetChanged();   //Refreshing Adapter

                return true;
            }

        });

    } // End  delete_task_Listener



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.checklist_menu, menu); //inflating already saved tasks
        return true;
    }




    public void add_Button_Listener()
    {

        lv_tasks = (ListView) findViewById(R.id.list_task);

        tasks = new ArrayList<String>();
        tasks_Adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, tasks);

        lv_tasks.setAdapter(tasks_Adapter);


        button = (Button) findViewById(R.id.button_add_task);

        button.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                final EditText inputField = new EditText(context);


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setTitle("Add Task");        //Setting Dialog Attributes

                alertDialogBuilder.setMessage("Please enter the new task");

                alertDialogBuilder.setView(inputField);

                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        String itemText = inputField.getText().toString();

                        tasks.add(itemText);

                        tasks_Adapter.notifyDataSetChanged();

                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();                               //Closing the dialog on Cancel
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create(); // creating alert dialog

                alertDialog.show();                                    // showing it.


            }// End method onClick

        });


        }// End method add_button_listener

}// End  ChecklistUI class
