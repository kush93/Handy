package com.example.kushal.Handy;



import com.example.kushal.rihabhbhandari.R;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
       // import android.support.design.widget.FloatingActionButton;
        //import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.view.Menu;
        import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import PersistanceLayer.TextNotePL;

import static android.R.layout.simple_list_item_1;
import static com.example.kushal.rihabhbhandari.R.layout.activity_main;

public class MainActivity extends Activity {
    ListView listView;
    Button newNote;
    TextNotePL textNotePL = new TextNotePL();
    int alSize = 0;// arraylist size
   // Note noteObj1=new Note();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateListView();
        //textNotePL.getNoteList().add()
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//
//
//            }
//        });

        newNote = (Button) findViewById(R.id.button_take_note);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NoteTaker.class);
                startActivityForResult(intent, 0);

            }
        });


    }


    private void populateListView() {
         listView = (ListView) findViewById(R.id.list);

        Note note1=new Note("Rahul","Bhandari","firstNote");
//
       textNotePL.setNoteList(note1);

        alSize = textNotePL.getNoteList().size();
       String noteName[] = new String[alSize];

        for (int i = 0; i < alSize; i++) {

            noteName[i] = textNotePL.getNoteList().get(i).noteName.toString();

        }

        ArrayList<String> noteList = new ArrayList<String>();
        noteList.addAll(Arrays.asList(noteName) );


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.view_note_data , R.id.textView_notename,noteList );


        listView.setAdapter(arrayAdapter);


        //listView.setOnItemClickListener();
    }



    }
//@Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
