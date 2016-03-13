package presentationlayer;

import android.app.Activity;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import businesslayer.TextNoteBL;
import persistancelayer.NoteInterface;

/**
 * Created by Matthias on 16-03-12.
 */
public class SampleNote extends Activity implements NoteInterface {
	private String title;
	private String contents;
	private String tags;
	private String time;
	private boolean isPinned;

	static TextNoteBL textNoteBL;
	private static String noteType = "textNote";

	public SampleNote(String title, String contents, String tags, String time, boolean isPinned) {
		this.title = title;
		this.contents = contents;
		this.tags = tags;
		this.time = time;
		this.isPinned = isPinned;
		textNoteBL = new TextNoteBL(this);
	}

	static List<SampleNote> getSampleNotes() {
		List<SampleNote> sampleNotes = new ArrayList<>();
		List<String> returnedNotes = new ArrayList<>();

		// Date date = new Date();

		returnedNotes=SampleNote.textNoteBL.getSavedData(noteType); // returns the data from the database for the textNote

		int listSize=returnedNotes.size();

		for(int i=0;i<listSize;i++)
		{
			//String noteName=

			String singleNote=returnedNotes.get(i);
			String token[]=singleNote.trim().split("\\s+");
			sampleNotes.add(new SampleNote(token[2],token[4],token[3],token[1],false));
		}


//		sampleNotes.add(new SampleNote(null, null, null, null, false));
//
//		List<String> tags = new ArrayList<>();
//		sampleNotes.add(new SampleNote("My Sample Note 1", "And Some Contents 1", tags, "2014", false));
//
//		tags.add("foo");
//		sampleNotes.add(new SampleNote("My Sample Note 2", "And Some Contents 2", tags, "2014", true));
//
//		tags.add("boo");
//		sampleNotes.add(new SampleNote("My Sample Note 3", "And Some Contents 3", tags, null, true));
//
//		tags.add("NA");
//		sampleNotes.add(new SampleNote("My Sample Note 3", "And Some Contents 3", tags, "2014", false));

		return sampleNotes;
	}

	@Override
	public boolean hasImages() {
		return false;
	}

	@Override
	public List<Bitmap> getImages() {
		return null;
	}

	@Override
	public boolean isPinned() {
		return isPinned;
	}

	@Override
	public boolean hasNoteTitle() {
		return title != null;
	}

	@Override
	public String getNoteTitle() {
		return null;
	}

	@Override
	public boolean hasContents() {
		return contents != null;
	}

	@Override
	public String getContents() {
		return contents;
	}

	@Override
	public boolean hasTag() {
		return tags != null && !tags.isEmpty();
	}

	@Override
	public String getTag() {
		return tags;
	}

	@Override
	public boolean hasLastEditedTime() {
		return time != null;
	}

	@Override
	public String getLastEditedTime() {
		return time;
	}
}