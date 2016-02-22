package com.example.kushal.Handy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.kushal.rihabhbhandari.R;

import BusinessLayer.TextNoteBL;
import PersistanceLayer.TextNotePL;

/**
 * Created by rishabhbhandari on 2016-02-19.
 */

public class NoteTaker extends Activity {

    Button addNote;
    //DataBaseHelper myDb;
    TextNoteBL textNoteBL=new TextNoteBL();
   TextNotePL textnoteObj=new TextNotePL();
    MainActivity mobj = new MainActivity();;

    TextView editName,editLabel,editNote;

    public void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_taker);
        // myDb=new DataBaseHelper(this);

        // textNoteBL=new TextNoteBL();
        editName=(TextView)findViewById(R.id.editText_name);
        editLabel=(TextView)findViewById(R.id.editText_label);
        editNote=(TextView)findViewById(R.id.editText_note);
        addNote=(Button)findViewById(R.id.button_save_note);
        addData();


    }

    public void addData()
    {
        addNote.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
//                boolean isInserted=myDb.insertData(editName.getText().toString(),editLabel.getText().toString(),editNote.getText().toString());
//                if (isInserted==true)
//                    Toast.makeText(NoteTaker.this,"Data Inserted",Toast.LENGTH_LONG).show();
//                else
//                    Toast.makeText(NoteTaker.this,"Data NOT Inserted",Toast.LENGTH_LONG).show();
                textNoteBL.create(editName.getText().toString(), editLabel.getText().toString(), editNote.getText().toString()); //calls the business logic class for text notes
                if(textnoteObj.getNoteList().isEmpty())
                {
                    Toast.makeText(NoteTaker.this,"Note was not saved" , Toast.LENGTH_LONG).show();

                }
                else
                {


                    Toast.makeText(NoteTaker.this,editName.getText().toString() + " Note was saved",Toast.LENGTH_LONG).show();
                    //mobj.dataAdded(editName);
                    MainActivity.getInstance().dataAdded(editName);
                }

                finish();

            }
        });
    }



}
