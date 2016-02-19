package com.example.kushal.Handy;

import TextNoteBL.TextNoteBL;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kushal.rihabhbhandari.R;

/**
 * Created by rishabhbhandari on 2016-02-19.
 */

public class NoteTaker extends Activity {

    Button addNote;
    //DataBaseHelper myDb;
    TextNoteBL textNoteBL=new TextNoteBL();
    TextNotePL textnoteObj=new TextNotePL();

    String editName,editLabel,editNote;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_taker);
        // myDb=new DataBaseHelper(this);

        // textNoteBL=new TextNoteBL();
        editName=findViewById(R.id.editText_name).toString();
        editLabel=findViewById(R.id.editText_label).toString();
        editNote=findViewById(R.id.editText_note).toString();
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
                textNoteBL.create(editName, editLabel, editNote); //calls the business logic class for text notes
                if(textnoteObj.getNoteList().isEmpty())
                {
                    Toast.makeText(NoteTaker.this, "data not inserted", Toast.LENGTH_LONG).show();


                }
                else
                {


                    Toast.makeText(NoteTaker.this,"data inserted",Toast.LENGTH_LONG).show();
                }

            }
        });
    }



}
