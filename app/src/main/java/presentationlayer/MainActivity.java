package presentationlayer;


import android.Manifest;
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
import android.app.Activity;
import com.example.kushal.rihabhbhandari.R;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;


import businesslayer.TextNoteBL;
import persistancelayer.NoteInterface;
import persistancelayer.TextNotePL;

public class MainActivity extends AppCompatActivity {
    private static final int PICKFILE_RESULT_CODE = 1;
    private int MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE;     // is used in requestStoragePermission()

    ListView listView;
    Button newNote;
    TextNotePL textNotePL = new TextNotePL();
    TextNoteBL textNoteBL;
    int alSize = 0;// arraylist size
    ArrayAdapter<String> arrayAdapter;
    NoteListAdapter noteListAdapter;
    static MainActivity mainObj;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textNoteBL = new TextNoteBL(this);

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
                startActivityForResult(intent, 0);

            }
        });


        newNote = (Button) findViewById(R.id.button_main_open_photo_note);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PhotoNoteUI.class);
                startActivity(intent);
            }
        });

        newNote = (Button) findViewById(R.id.button_main_open_hand_writing);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HandwritingUI.class);
                startActivity(intent);
            }
        });
        newNote=(Button) findViewById(R.id.button_pdf);
        newNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, PICKFILE_RESULT_CODE);
            }
        });

        newNote = (Button) findViewById(R.id.button_checklist);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChecklistUI.class);
                startActivity(intent);

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PICKFILE_RESULT_CODE) {

            String Fpath = data.getDataString();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(Fpath), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        }

    }

    public static MainActivity getInstance() {
        return mainObj;
    }

    private void populateListView() {

        List<String> textNoteData = new ArrayList<String>();
 //       textNoteData = textNoteBL.getSavedData("textNote");

        listView = (ListView) findViewById(R.id.listView_main_note_list);

////        previous version
////        alSize = textNotePL.getNoteList().size();
////        String noteName[] = new String[alSize];
////        for (int i = 0; i < alSize; i++) {
////            noteName[i] = textNotePL.getNoteList().get(i).noteName.toString();
////        }
////        ArrayList<String> noteList = new ArrayList<String>();
////        noteList.addAll(Arrays.asList(noteName) );
////        arrayAdapter = new ArrayAdapter<String>(this, R.layout.view_note_data , R.id.textView_notename,noteList );
        // with NoteInterface

        // with NoteInterface
        List<NoteInterface> noteList = new ArrayList<>();

        int size = 0;

        for (SampleNote sampleNote : SampleNote.getSampleNotes()) {
            System.out.println("OUTPUT: size: " + (++size));
            noteList.add(sampleNote);
        }
//        noteList.addAll(SampleNote.getSampleNotes());


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

    // @Override
    /*public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://presentationlayer/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://presentationlayer/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }*/
}