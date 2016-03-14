package businesslayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import persistancelayer.NoteInterface;

/**
 * Created by Matthias on 16-03-12.
 */
public class TextNoteWrapper extends Activity implements NoteInterface {
	private String title;
	private String contents;
	private String tags;
	private String time;
	private boolean isPinned;

	//static TextNoteBL textNoteBL;
	TextNoteBL textNoteBL;
	private static String noteType = "textNote";

	public TextNoteWrapper(String title, String contents, String tags, String time, boolean isPinned) {
		this.title = title;
		this.contents = contents;
		this.tags = tags;
		this.time = time;
		this.isPinned = isPinned;

	}
	public TextNoteWrapper(Context context)
	{
		textNoteBL = new TextNoteBL(context);

	}


	public List<TextNoteWrapper> getSampleNotes(String noteType) {

		List<TextNoteWrapper> textNoteWrappers = new ArrayList<>();
		List<String> returnedNotes = new ArrayList<String>();


		/*textNoteWrappers.add(new TextNoteWrapper("My Sample Note 1", "And Some Contents 1", "COMP3350", "2014", false));
		textNoteWrappers.add(new TextNoteWrapper("My Sample Note 2", "And Some Contents 2", "COMP3380", "2015", false));
*/
		returnedNotes=textNoteBL.getSavedData(noteType); // returns the data from the database for the textNote

		int listSize=returnedNotes.size();
		if(listSize>0)
		{
			for(int i=0;i<listSize;i++)
			{
				//String noteName=

				String singleNote=returnedNotes.get(i);
				String token[]=singleNote.trim().split("/");
				textNoteWrappers.add(new TextNoteWrapper(token[2],token[4],token[3],token[1],false));
			}

		}




//		textNoteWrappers.add(new TextNoteWrapper(null, null, null, null, false));
//
//		List<String> tags = new ArrayList<>();
//
//
//		tags.add("foo");
//		textNoteWrappers.add(new TextNoteWrapper("My Sample Note 2", "And Some Contents 2", tags, "2014", true));
//
//		tags.add("boo");
//		textNoteWrappers.add(new TextNoteWrapper("My Sample Note 3", "And Some Contents 3", tags, null, true));
//
//		tags.add("NA");
//		textNoteWrappers.add(new TextNoteWrapper("My Sample Note 3", "And Some Contents 3", tags, "2014", false));

		return textNoteWrappers;
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
		return title != null && !title.isEmpty();
	}

	@Override
	public String getNoteTitle() {
		return title;
	}

	@Override
	public boolean hasContents() {
		return contents != null && !contents.isEmpty();
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
		return time != null && !time.isEmpty();
	}

	@Override
	public String getLastEditedTime() {
		return time;
	}
}