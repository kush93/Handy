package com.example.kushal.Handy;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kushal.rihabhbhandari.R;

import java.util.ArrayList;
import java.util.Arrays;

import persistancelayer.TextNotePL;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button newNote;
    TextNotePL textNotePL = new TextNotePL();
    int alSize = 0;// arraylist size
    ArrayAdapter<String> arrayAdapter;
    static MainActivity mainObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainObj = this;
        setContentView(R.layout.activity_main);
        populateListView();
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


        newNote = (Button) findViewById(R.id.button_open_photonote);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PhotoNoteUI.class);
                startActivity(intent);
            }
        });

        newNote = (Button) findViewById(R.id.button_handwrite);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HandwritingUI.class);
                startActivity(intent);
            }
        });
    }


    public static MainActivity getInstance(){
        return mainObj;
    }

    private void populateListView() {


        Note note1=new Note("Rahul","LabelName","firstNote");
        Note note2 =new Note("Itr1","comp 3350","iteration 1 description");
        Note note3=new Note("Handy","comp 3350","TextNote feature description");
        textNotePL.createNoteList(note1);
        textNotePL.createNoteList(note2);
        textNotePL.createNoteList(note3);

        listView= (ListView) findViewById(R.id.listView);;

        alSize = textNotePL.getNoteList().size();
        String noteName[] = new String[alSize];

        for (int i = 0; i < alSize; i++) {

            noteName[i] = textNotePL.getNoteList().get(i).noteName.toString();

        }

        ArrayList<String> noteList = new ArrayList<String>();
        noteList.addAll(Arrays.asList(noteName) );


        arrayAdapter = new ArrayAdapter<String>(this, R.layout.view_note_data , R.id.textView_notename,noteList );

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {



            }
        });
    }


    public void dataAdded(TextView noteName)
    {
        //TextView nameET=(TextView)findViewById(R.id.);
        String name=noteName.getText().toString();
        Log.d("my tag", name) ;
//        Toast.makeText(getApplicationContext(),  name, Toast.LENGTH_SHORT).show();

        if(!name.isEmpty() && name.length()>0)
        {
            arrayAdapter.add(name);

            arrayAdapter.notifyDataSetChanged();

            //Toast.makeText(getApplicationContext(), "Note Added" + name, Toast.LENGTH_SHORT).show();
        }




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

