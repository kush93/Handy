


package presentationlayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kushal.rihabhbhandari.R;

import java.text.DateFormat;
import java.util.Date;

import businesslayer.TextNoteBL;
import businesslayer.TextNoteWrapper;
import persistancelayer.NoteInterface;
import persistancelayer.TextNotePL;

/**
 * Created by rishabhbhandari on 2016-02-19.
 */

public class NoteTakerUI extends Activity {

    Button addNote;
    //DataBaseHelper myDb;
    TextNoteBL textNoteBL = new TextNoteBL(this);
    TextNotePL textnoteObj = new TextNotePL();
    MainActivity mobj = new MainActivity();

    TextView editName, editLabel, editNote;
    final String noteType = "textNote";
    final String filePath = null;
    private String noteID;

    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_taker);
        // myDb=new DataBaseHelper(this);

        // textNoteBL=new TextNoteBL();
        editName = (TextView) findViewById(R.id.editText_name);
        editLabel = (TextView) findViewById(R.id.editText_label);
        editNote = (TextView) findViewById(R.id.editText_note);
        addNote = (Button) findViewById(R.id.button_save_note);
        //editName.setTypeface(null, Typeface.BOLD_ITALIC);

        // for open from save
        Intent intent = this.getIntent();
        if (intent != null && intent.getBooleanExtra(NoteInterface.OPEN_SAVED, false))
        {
            TextNoteWrapper wrapper = (TextNoteWrapper) intent.getSerializableExtra(NoteInterface.DATA);

            assert (wrapper != null);

            if (wrapper.hasNoteTitle())
                editName.setText(wrapper.getNoteTitle());

            if (wrapper.hasContents())
                editNote.setText(wrapper.getContents());

            if (wrapper.hasTag())
                editLabel.setText(wrapper.getTag());

            noteID = wrapper.getNoteID();
            modifyData();

        }
        else
            addData();


    }

    public static void openNote(Context context, TextNoteWrapper wrapper)
    {
        Intent intent = new Intent(context, NoteTakerUI.class);
        intent.putExtra(NoteInterface.OPEN_SAVED, true);
        intent.putExtra(NoteInterface.DATA, wrapper);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ((Activity) context).startActivityForResult(intent, MainActivity.REQUEST_NEW_NOTE);
    }

    public void addData() //calls the onClick method for Save button
    {
        addNote.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View v)
            {

                String editedTime = DateFormat.getDateTimeInstance().format(new Date());
                // long representation of current time


                if (editName.getText().toString().isEmpty())
                {
                    Toast.makeText(NoteTakerUI.this, "Title not specified", Toast.LENGTH_LONG).show();

                }
                else
                {

                    boolean isInserted= textNoteBL.create(editedTime, editName.getText().toString(), editLabel.getText().toString(), editNote.getText().toString(), filePath, noteType); //calls the business logic class for text notes
                    if (isInserted == true)
                        Toast.makeText(NoteTakerUI.this, "Note was saved", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(NoteTakerUI.this, "Note was not saved", Toast.LENGTH_LONG).show();

                    // MainActivity.getInstance().dataAdded(editText_title);// not being used
                   finish();
                }






            }
        });
    }


    public void modifyData() //calls the onClick method for Save button
    {
        addNote.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String editedTime = DateFormat.getDateTimeInstance().format(new Date());
                // long representation of current time

                textNoteBL.updateData(noteID, editedTime, editName.getText().toString(), editLabel.getText().toString(), editNote.getText().toString(), filePath, noteType); //calls the business logic class for text notes
                if (textnoteObj.getData("note").isEmpty()) {
                    Toast.makeText(NoteTakerUI.this, "Note was not modified", Toast.LENGTH_LONG).show();

                } else {


                    Toast.makeText(NoteTakerUI.this, editName.getText().toString() + " Note was modified", Toast.LENGTH_LONG).show();
                    //mobj.dataAdded(editName);
                    // MainActivity.getInstance().dataAdded(editName); // calls the method dataAdded() from the mainActivity using the getInstance method
                }

                finish();

            }
        });
    }




}
