package presentationlayer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.kushal.rihabhbhandari.R;

/**
 * Created by Abdul Hadi on 11/03/2016.
 */




public class ChecklistUI extends Activity
{

    Button button;
    private ArrayList<String> tasks;
    private ArrayAdapter<String> tasks_Adapter;
    private ListView lv_tasks;

    final Context context = this;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);



        lv_tasks = (ListView) findViewById(R.id.list_task);
        tasks = new ArrayList<String>();
        tasks_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);
        lv_tasks.setAdapter(tasks_Adapter);

        add_Button_Listener();
        delete_task_Listener();

    }



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

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();                               //Closing the dialog on Cancel
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create(); // creating alert dialog

                alertDialog.show();                                    // showing it


            }// End method onClick

        });

    }// End method add_button_listener

}// End  ChecklistUI class
