package com.example.kushal.Handy;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kushal.rihabhbhandari.R;

import java.util.ArrayList;
import java.util.Arrays;

import PersistanceLayer.TextNotePL;

// import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;

public class MainActivity extends Activity {
    ListView listView;
    Button newNote;
    TextNotePL textNotePL = new TextNotePL();
    int alSize = 0;// arraylist size
    ArrayAdapter<String> arrayAdapter;
    // Note noteObj1=new Note();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateListView();



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


        Note note1=new Note("Rahul","LabelName","firstNote");
        Note note2 =new Note("Itr1","comp 3350","iteration 1 description");
        Note note3=new Note("Handy","comp 3350","TextNote feature description");
        textNotePL.createNoteList(note1);
        textNotePL.createNoteList(note2);
        textNotePL.createNoteList(note3);

        listView= (ListView) findViewById(R.id.list);;

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

            Toast.makeText(getApplicationContext(), "Note Added" + name, Toast.LENGTH_SHORT).show();
        }




    }
}