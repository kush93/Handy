package presentationlayer;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kushal.rihabhbhandari.R;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import businesslayer.TextNoteBL;
import persistancelayer.NoteInterface;
import persistancelayer.TextNotePL;

public class MainActivity extends AppCompatActivity {

    private int MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE;     // is used in requestStoragePermission()

    ListView listView;
    Button newNote;
    TextNotePL textNotePL = new TextNotePL();
    TextNoteBL textNoteBL;
    int alSize = 0;// arraylist size
    ArrayAdapter<String> arrayAdapter;
    NoteListAdapter noteListAdapter;
    static MainActivity mainObj;

	private int REQUEST_NEW_NOTE = 1;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //textNoteBL = new TextNoteBL(this);

        // request permission ... required for API 23 or above
        requestStoragePermission();


        mainObj = this;
        setContentView(R.layout.activity_main);
       populateListView();

        newNote = (Button) findViewById(R.id.button_main_open_text_note);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NoteTakerUI.class);
	            startActivityForResult(intent, REQUEST_NEW_NOTE);
            }
        });


        newNote = (Button) findViewById(R.id.button_main_open_photo_note);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PhotoNoteUI.class);
                startActivityForResult(intent, REQUEST_NEW_NOTE);
            }
        });

        newNote = (Button) findViewById(R.id.button_main_open_hand_writing);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HandwritingUI.class);
	            startActivityForResult(intent, REQUEST_NEW_NOTE);
            }
        });

        newNote = (Button) findViewById(R.id.button_checklist);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChecklistUI.class);
	            startActivityForResult(intent, REQUEST_NEW_NOTE);

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_NEW_NOTE) notifyDataSetChanged();
	}

    public static MainActivity getInstance() {
        return mainObj;
    }

    private void populateListView() {

        List<SampleNote> textNoteData = new ArrayList<SampleNote>();

        listView = (ListView) findViewById(R.id.listView_main_note_list);

        // with NoteInterface
        List<NoteInterface> noteList = new ArrayList<>();

        SampleNote sampleNote=new SampleNote(this);

        textNoteData=sampleNote.getSampleNotes("textNote");
        int listSize=textNoteData.size();
        for(int i=0; i<listSize;i++)
        {
            noteList.add(textNoteData.get(i));
        }

        noteListAdapter = new NoteListAdapter(this, noteList);
        listView.setAdapter(noteListAdapter);
        noteListAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Toast.makeText(MainActivity.this, "Opening a note is not yet implemented", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void notifyDataSetChanged()
    {
	    populateListView();
    }

    public void dataAdded(TextView noteName) {
        //TextView nameET=(TextView)findViewById(R.id.);
        String name = noteName.getText().toString();
        Log.d("my tag", name);
//        Toast.makeText(getApplicationContext(),  name, Toast.LENGTH_SHORT).show();

        if (!name.isEmpty() && name.length() > 0) {
            arrayAdapter.add(name);

            arrayAdapter.notifyDataSetChanged();

            //Toast.makeText(getApplicationContext(), "Note Added" + name, Toast.LENGTH_SHORT).show();
        }


    }

    // request permission ... required for API 23 or above
    private void requestStoragePermission() {
        if ((ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))

        {
            ActivityCompat.requestPermissions
                    (MainActivity.this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE);
        }
    }

}