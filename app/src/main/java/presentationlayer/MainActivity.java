package presentationlayer;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.example.kushal.rihabhbhandari.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import businesslayer.HandwritingWrapper;
import businesslayer.PhotoNoteWrapper;
import businesslayer.TextNoteBL;
import businesslayer.TextNoteWrapper;
import persistancelayer.NoteInterface;
import persistancelayer.TextNotePL;

public class MainActivity extends AppCompatActivity {
    private static final int PICKFILE_RESULT_CODE = 1;
    private static final int LONG_DIALOG_REMOVE_RESULT_CODE = 2;
    private int MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE;     // is used in requestStoragePermission()

    ListView listView;
    Button newNote;
    TextNotePL textNotePL = new TextNotePL();
    TextNoteBL textNoteBL;
    ArrayAdapter<String> arrayAdapter;
    NoteListAdapter noteListAdapter;
    static MainActivity mainObj;

    public static final int REQUEST_NEW_NOTE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //textNoteBL = new TextNoteBL(this);

        // request permission ... required for API 23 or above
        requestStoragePermission();

        textNoteBL = new TextNoteBL(this);

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
        newNote = (Button) findViewById(R.id.button_pdf);
        newNote.setOnClickListener(new View.OnClickListener() {
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
                startActivityForResult(intent, REQUEST_NEW_NOTE);

            }
        });


        newNote = (Button) findViewById(R.id.button_calendar);
        //newNote.setOnClickListener(onClickListener();
        newNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 14) {
                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 60 * 000)
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 0)
//                                .putExtra(CalendarContract.Events.TITLE, "Yoga")
//                                .putExtra(CalendarContract.Events.DESCRIPTION, "Group class")
//                                .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym")
                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
//                                .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
                    startActivity(intent);
                } else {
                    Calendar cal = Calendar.getInstance();
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra("beginTime", cal.getTimeInMillis());
//                        intent.putExtra("allDay", true);
//                        intent.putExtra("rrule", "FREQ=YEARLY");
//                        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
//                        intent.putExtra("title", "A Test Event from android app");
                    startActivity(intent);
                }


            }

    });






        newNote = (Button) findViewById(R.id.button_dnd);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    Toast.makeText(MainActivity.this, "Do Not Disturb Mode is ON", Toast.LENGTH_LONG).show();
                } else {

                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    Toast.makeText(MainActivity.this, "Do Not Disturb Mode is OFF", Toast.LENGTH_LONG).show();
                }

            }
        });
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

    @Override
    protected void onResume() {
        super.onResume();
        notifyDataSetChanged();
    }

    public static MainActivity getInstance() {
        return mainObj;
    }

    private void populateListView() {
        listView = (ListView) findViewById(R.id.listView_main_note_list);

        // with NoteInterface
        final List<NoteInterface> noteList = new ArrayList<>();

        TextNoteWrapper textNoteWrapper = new TextNoteWrapper(this);

        List<NoteInterface> textNoteData = getNotesFromDB("all");

        int listSize = textNoteData.size();
        for (int i = 0; i < listSize; i++) {
            noteList.add(textNoteData.get(i));
        }

        noteListAdapter = new NoteListAdapter(this, noteList);
        listView.setAdapter(noteListAdapter);
        noteListAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id)
	        {

		        noteList.get(position).openNote(MainActivity.this);
		        //	            ((NoteInterface) parent.getSelectedItem()).openNote();
		        //                Toast.makeText(MainActivity.this, "Opening a note is not yet implemented", Toast.LENGTH_SHORT).show();

	        }
        });

		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				showDialogOnLongClickListItem(noteList.get(position), noteList);
				return true;
			}
		});
    }

    private void notifyDataSetChanged() {
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


    public List<NoteInterface> getNotesFromDB(String noteType) {

        List<NoteInterface> noteInterfaces = new ArrayList<>();
        List<String> returnedNotesRawData = textNoteBL.getSavedData(noteType); // returns the data from the database for the textNote

        int listSize = returnedNotesRawData.size();
        if (listSize > 0) {
            for (int i = 0; i < listSize; i++) {
                //String noteName=

                String singleNote = returnedNotesRawData.get(i);
                String token[] = singleNote.trim().split("\\?");

                //id?time?name?label?textNote?filePaths?noteType
                // textNote1/textNote2/textNote3/...... = token[4]
                // filePath1^filePath2^filePath3^......= token[5]

                String currNoteType = token[6];

                switch (currNoteType) {
                    case NoteInterface.textNoteType:
                        noteInterfaces.add(new TextNoteWrapper(token[0], token[2], token[4], token[3], token[1], token[5], false));
                        break;
                    case NoteInterface.handWritingNoteType:
                        noteInterfaces.add(new HandwritingWrapper(token[0], token[2], token[4], token[3], token[1], token[5], false));
                        break;
                    case NoteInterface.photoNoteType:
                        noteInterfaces.add(new PhotoNoteWrapper(token[0], token[2], token[4], token[3], token[1], token[5], false));
                        break;
                   case NoteInterface.checkListType:
                   	noteInterfaces.add(new CheckListWrapper(token[0], token[2], token[4], token[3], token[1], token[5], false));
                   	break;

                    default:
                        noteInterfaces.add(new TextNoteWrapper(token[0], token[2], token[4], token[3], token[1], token[5], false));

                }
            }
        }

        return noteInterfaces;
    }

	private void showDialogOnLongClickListItem(final NoteInterface iNote, final List<NoteInterface> noteList)
	{
		final int[] result = new int[1];

		final CharSequence[] items = {"Remove", "Cancel"};

		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("Options for this note");
		builder.setItems(items, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int item)
			{
				if (items[item].equals("Remove"))
				{
					Toast.makeText(MainActivity.this, "Removed", Toast.LENGTH_SHORT).show();
					String noteID = iNote.getNoteID();
					textNoteBL.delete(noteID);
					noteList.remove(iNote);
					noteListAdapter.notifyDataSetChanged();
				}
				else if (items[item].equals("Cancel"))
				{
					result[0] = -1;
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}
}

