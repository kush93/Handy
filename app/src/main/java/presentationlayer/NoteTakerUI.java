


package presentationlayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kushal.rihabhbhandari.R;

import businesslayer.TextNoteBL;
import persistancelayer.DataInterface;
import persistancelayer.TextNotePL;

import java.util.List;

/**
 * Created by rishabhbhandari on 2016-02-19.
 */

public class NoteTakerUI extends Activity
{
	Button addNote;

    //DataBaseHelper myDb;
    TextNoteBL textNoteBL=new TextNoteBL(this);
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

        //editName.setTypeface(null, Typeface.BOLD_ITALIC);

        addData();


    }

    public void addData() //calls the onClick method for Save button
    {
        addNote.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {

              
                textNoteBL.create(editName.getText().toString(), editLabel.getText().toString(), editNote.getText().toString()); //calls the business logic class for text notes
                if(textnoteObj.getNoteList().isEmpty())
                {
                    Toast.makeText(NoteTakerUI.this,"Note was not saved" , Toast.LENGTH_LONG).show();

                }
                else
                {


                    Toast.makeText(NoteTakerUI.this,editName.getText().toString() + " Note was saved",Toast.LENGTH_LONG).show();
                    //mobj.dataAdded(editName);
                    MainActivity.getInstance().dataAdded(editName); // calls the method dataAdded() from the mainActivity using the getInstance method
                }

                finish();

            }
        });
    }



}
